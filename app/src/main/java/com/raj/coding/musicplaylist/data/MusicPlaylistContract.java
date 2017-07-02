package com.raj.coding.musicplaylist.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class MusicPlaylistContract {

    public static final String CONTENT_AUTHORITY = "com.raj.coding.musicplaylist.data";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PLAYLIST = "playlist";
    public static final String PATH_SONGS = "songs";

    public static final class PlaylistEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PLAYLIST);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYLIST;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYLIST;

        public static final String TABLE_NAME = "playlist";
        public static final String _ID = BaseColumns._ID;
        public static final String PLAYLIST_NAME = "playlist_name";
    }

    public static final class SongEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SONGS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SONGS;

        public static final String TABLE_NAME = "songs";
        public static final String SONG_ID = BaseColumns._ID;
        public static final String SONG_NAME = "song_name";
        public static final String SONG_SINGER = "song_singer";
        public static final String PLAYLIST_ID = "playlist_id";
    }
}
