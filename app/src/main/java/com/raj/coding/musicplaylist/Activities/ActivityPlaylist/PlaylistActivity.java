package com.raj.coding.musicplaylist.Activities.ActivityPlaylist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.raj.coding.musicplaylist.Activities.ActivitySongList.SongsListActivity;
import com.raj.coding.musicplaylist.Activities.ActivitySongList.SongsListFragment;
import com.raj.coding.musicplaylist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaylistActivity extends AppCompatActivity implements PlaylistActivityFragment.PlaylistInterface {

    private static final String ADD_PLAYLIST_DIALOG = "add_playlist_dialog";
    public static final String PLAYLIST_NAME = "playlist_name";
    private static final String SONGS_LIST = "songs_list";
    public static final String URI_NAME = "uri_name";
    private boolean mTablet;

    @Nullable
    @BindView(R.id.playlist_fragment_container)
    FrameLayout frameLayout;

    public PlaylistActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        mTablet = (frameLayout != null);

    }

    //used to verify if the device has a smallest width of 600dp
    public boolean isTablet() {
        return mTablet;
    }

    //To display the add playlist dialog
    @Override
    public void displayAddPlaylist() {
        AddPlaylistFragment addPlaylistFragment = new AddPlaylistFragment();
        addPlaylistFragment.setCancelable(false);
        addPlaylistFragment.show(getSupportFragmentManager(), ADD_PLAYLIST_DIALOG);
    }

    //to create and render a fragment when in sw600dp mode
    private void displaySongsFragment(Uri uri, String playlistName) {
        SongsListFragment songsListFragment = new SongsListFragment();
        Bundle args = new Bundle();
        args.putString(URI_NAME, uri.toString());
        args.putString(PLAYLIST_NAME, playlistName);
        songsListFragment.setArguments(args);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.playlist_fragment_container, songsListFragment,
                        SONGS_LIST)
                .commit();
    }

    public void displaySongs(Uri uri, String playlistName) {

        if (!isTablet()) {
            Intent intent = new Intent(this, SongsListActivity.class);
            intent.setData(uri);
            intent.putExtra(PLAYLIST_NAME, playlistName);
            startActivity(intent);
        } else {
                displaySongsFragment(uri, playlistName);
        }
    }
}
