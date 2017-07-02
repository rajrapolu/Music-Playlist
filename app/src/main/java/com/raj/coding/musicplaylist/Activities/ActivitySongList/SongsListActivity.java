package com.raj.coding.musicplaylist.Activities.ActivitySongList;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.raj.coding.musicplaylist.R;

public class SongsListActivity extends AppCompatActivity {

    private static final String SONGS_LIST_FRAGMENT = "songs_list_fragment";
    public static final String PLAYLIST_URI = "playlist_uri";
    public static final String PLAYLIST_NAME = "playlist_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_list);

        Uri uri = getIntent().getData();
        String playlistName = getIntent().getStringExtra(PLAYLIST_NAME);

        //creating and addding the fragment
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            SongsListFragment songsListFragment = new SongsListFragment();
            args.putString(PLAYLIST_URI, String.valueOf(uri));
            args.putString(PLAYLIST_NAME, playlistName);
            songsListFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                    songsListFragment, SONGS_LIST_FRAGMENT).commit();
        }
    }
}
