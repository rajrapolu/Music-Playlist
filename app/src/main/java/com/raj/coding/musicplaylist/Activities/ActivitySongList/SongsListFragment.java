package com.raj.coding.musicplaylist.Activities.ActivitySongList;


import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raj.coding.musicplaylist.Activities.ActivityPlaylist.PlaylistActivity;
import com.raj.coding.musicplaylist.DividerRecyclerView;
import com.raj.coding.musicplaylist.R;
import com.raj.coding.musicplaylist.data.MusicPlaylistContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongsListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int SONG_LOADER = 2;
    private SongsListAdapter songsListAdapter;
    private Unbinder unbinder;
    private Uri uri;

    @BindView(R.id.recycler_view_song)
    RecyclerView mRecyclerView;

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.empty_songs_image)
    TextView emptyView;

    @Nullable
    @BindView(R.id.tv_playlist_name)
    TextView mPlaylist;

    @Optional
    @OnClick(R.id.fab_songs)
    public void onFabSongClicked() {
        //adding dummy songs based on playlist
        addDummySongs(uri);
    }


    public SongsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_songs_list, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        settingupUI();

        songsListAdapter = new SongsListAdapter(getContext());

        //adding lines to seperate items
        RecyclerView.ItemDecoration divider;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            divider = new DividerRecyclerView(getResources()
                    .getDrawable(R.drawable.line_divider, null));
        } else {
            divider = new DividerRecyclerView(getResources()
                    .getDrawable(R.drawable.line_divider));
        }
        mRecyclerView.addItemDecoration(divider);

        mRecyclerView.setAdapter(songsListAdapter);
        return rootView;
    }

    //settingup the corresponding UI for phone and tablet
    private void settingupUI() {
        String playlistName;
        if (getContext() instanceof PlaylistActivity) {
            uri = Uri.parse(getArguments().getString(PlaylistActivity.URI_NAME));
            playlistName = getArguments().getString(PlaylistActivity.PLAYLIST_NAME);
            if (mPlaylist != null) {
                mPlaylist.setText(playlistName);
            }
        } else {
            uri = Uri.parse(getArguments().getString(SongsListActivity.PLAYLIST_URI));
            playlistName = getArguments().getString(SongsListActivity.PLAYLIST_NAME);

            ((AppCompatActivity)(getContext())).setSupportActionBar(toolbar);
            ((AppCompatActivity)(getContext())).getSupportActionBar().setTitle(playlistName);

            if (toolbar != null) {
                toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });
            }

            setHasOptionsMenu(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.songs_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_add_songs) {
            addDummySongs(uri);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initializing the loader
        getActivity().getSupportLoaderManager().restartLoader(SONG_LOADER, null, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //adding songs to the playlist
    private void addDummySongs(Uri uri) {
        if (ContentUris.parseId(uri) == 1) {
            for (int i = 0; i < 3; i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MusicPlaylistContract.SongEntry.SONG_NAME,
                        getString(R.string.song_name) + i);
                contentValues.put(MusicPlaylistContract.SongEntry.SONG_SINGER,
                        getString(R.string.singer_name) + i);
                contentValues.put(MusicPlaylistContract.SongEntry.PLAYLIST_ID,
                        ContentUris.parseId(uri));
                getContext().getContentResolver().insert(MusicPlaylistContract.SongEntry.CONTENT_URI,
                        contentValues);
            }
        } else {
            for (int i = 5; i < 8; i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MusicPlaylistContract.SongEntry.SONG_NAME,
                        getString(R.string.song_name) + i);
                contentValues.put(MusicPlaylistContract.SongEntry.SONG_SINGER,
                        getString(R.string.singer_name) + i);
                contentValues.put(MusicPlaylistContract.SongEntry.PLAYLIST_ID,
                        ContentUris.parseId(uri));
                getContext().getContentResolver().insert(MusicPlaylistContract.SongEntry.CONTENT_URI,
                        contentValues);
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MusicPlaylistContract.SongEntry.TABLE_NAME + "." + MusicPlaylistContract.SongEntry.SONG_ID,
                MusicPlaylistContract.SongEntry.SONG_NAME,
                MusicPlaylistContract.SongEntry.SONG_SINGER,
                MusicPlaylistContract.SongEntry.PLAYLIST_ID
        };
        return new CursorLoader(getActivity(),
                ContentUris.withAppendedId(MusicPlaylistContract.SongEntry.CONTENT_URI,
                        ContentUris.parseId(uri)),
                projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() <= 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
        songsListAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        songsListAdapter.swapCursor(null);
    }
}
