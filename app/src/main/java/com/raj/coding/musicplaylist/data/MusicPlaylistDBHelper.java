package com.raj.coding.musicplaylist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.raj.coding.musicplaylist.data.MusicPlaylistContract.PlaylistEntry;
import static com.raj.coding.musicplaylist.data.MusicPlaylistContract.SongEntry;

public class MusicPlaylistDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "musicplaylist.db";
    private static final int DATABASE_VERSION = 1;

    public MusicPlaylistDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //SQL statement for creating playlist table
        String SQL_CREATE_PLAYLIST_STATEMENT = "CREATE TABLE " + PlaylistEntry.TABLE_NAME + "("
                + PlaylistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PlaylistEntry.PLAYLIST_NAME + " TEXT UNIQUE NOT NULL);";

        String SQL_CREATE_SONGLIST_STATEMENT = "CREATE TABLE " + SongEntry.TABLE_NAME + "("
                + SongEntry.SONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SongEntry.SONG_NAME + " TEXT NOT NULL, "
                + SongEntry.SONG_SINGER + " TEXT NOT NULL, "
                + SongEntry.PLAYLIST_ID + " INTEGER);";
        db.execSQL(SQL_CREATE_PLAYLIST_STATEMENT);
        db.execSQL(SQL_CREATE_SONGLIST_STATEMENT);
    }

    //no logic in this method because it is in the initial version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
