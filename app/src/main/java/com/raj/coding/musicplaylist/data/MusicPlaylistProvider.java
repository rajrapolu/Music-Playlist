package com.raj.coding.musicplaylist.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class MusicPlaylistProvider extends ContentProvider {

    private MusicPlaylistDBHelper musicPlaylistDBHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int PLAYLIST = 1;
    private static final int PLAYLIST_ID = 2;
    private static final int SONGLIST = 3;
    private static final int SONGLIST_ID = 4;
    private static final SQLiteQueryBuilder playlistSongsQueryBuilder = new SQLiteQueryBuilder();

    static {
        sUriMatcher.addURI(MusicPlaylistContract.CONTENT_AUTHORITY,
                MusicPlaylistContract.PATH_PLAYLIST, PLAYLIST);
        sUriMatcher.addURI(MusicPlaylistContract.CONTENT_AUTHORITY,
                MusicPlaylistContract.PATH_PLAYLIST + "/#",
                PLAYLIST_ID);
        sUriMatcher.addURI(MusicPlaylistContract.CONTENT_AUTHORITY,
                MusicPlaylistContract.PATH_SONGS, SONGLIST);
        sUriMatcher.addURI(MusicPlaylistContract.CONTENT_AUTHORITY,
                MusicPlaylistContract.PATH_SONGS + "/#", SONGLIST_ID);

        //forms from in the query
        playlistSongsQueryBuilder.setTables(
                MusicPlaylistContract.SongEntry.TABLE_NAME + " INNER JOIN "
                        + MusicPlaylistContract.PlaylistEntry.TABLE_NAME + " ON "
                        + MusicPlaylistContract.SongEntry.TABLE_NAME + "."
                        + MusicPlaylistContract.SongEntry.PLAYLIST_ID + " = "
                        + MusicPlaylistContract.PlaylistEntry.TABLE_NAME + "."
                        + MusicPlaylistContract.PlaylistEntry._ID);
    }

    //playlist.playlist_id = ?
    private static final String playlistSelection = MusicPlaylistContract.SongEntry.PLAYLIST_ID + " = ? ";

    @Override
    public boolean onCreate() {
        musicPlaylistDBHelper = new MusicPlaylistDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = musicPlaylistDBHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case PLAYLIST:
                cursor = database.query(MusicPlaylistContract.PlaylistEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case PLAYLIST_ID:
                selection = MusicPlaylistContract.PlaylistEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

                cursor = database.query(MusicPlaylistContract.PlaylistEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case SONGLIST:
                cursor = database.query(MusicPlaylistContract.SongEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case SONGLIST_ID:
                cursor = getPlaylistSongs(uri, projection, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    private Cursor getPlaylistSongs(Uri uri, String[] projection, String sortOrder) {
        return playlistSongsQueryBuilder.query(musicPlaylistDBHelper.getReadableDatabase(),
                projection,
                playlistSelection,
                new String[]{String.valueOf(ContentUris.parseId(uri))},
                null,
                null,
                sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PLAYLIST:
                return MusicPlaylistContract.PlaylistEntry.CONTENT_LIST_TYPE;
            case PLAYLIST_ID:
                return MusicPlaylistContract.PlaylistEntry.CONTENT_ITEM_TYPE;
            case SONGLIST:
                return MusicPlaylistContract.SongEntry.CONTENT_LIST_TYPE;
            default:
                throw new IllegalStateException("Unknown uri " + uri + "with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PLAYLIST:
                return insertPlaylist(uri, values);
            case SONGLIST:
                return insertSong(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertSong(Uri uri, ContentValues values) {
        SQLiteDatabase database = musicPlaylistDBHelper.getWritableDatabase();
        String songName = values.getAsString(MusicPlaylistContract.SongEntry.SONG_NAME);
        if (songName == null) {
            throw new IllegalArgumentException("Song requires a name");
        }
        long rowId = database.insert(MusicPlaylistContract.SongEntry.TABLE_NAME, null, values);
        if (rowId == -1) {
            Toast.makeText(getContext(), "Failed to insert song", Toast.LENGTH_SHORT).show();
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, rowId);
    }

    private Uri insertPlaylist(Uri uri, ContentValues values) {
        SQLiteDatabase database = musicPlaylistDBHelper.getWritableDatabase();

        //checking data validation
        String playlistName = values.getAsString(MusicPlaylistContract.PlaylistEntry.PLAYLIST_NAME);
        if (playlistName == null) {
            throw new IllegalArgumentException("Playlist requires a name to be added");
        }
        long rowId = database.insert(MusicPlaylistContract.PlaylistEntry.TABLE_NAME, null, values);
        if (rowId == -1) {
            Toast.makeText(getContext(), "Failed to insert playlist", Toast.LENGTH_SHORT).show();
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, rowId);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = musicPlaylistDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case SONGLIST:
                rowsDeleted = database.delete(MusicPlaylistContract.SongEntry.TABLE_NAME,
                        selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Delete cannoyt be performed for " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;

    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }
}
