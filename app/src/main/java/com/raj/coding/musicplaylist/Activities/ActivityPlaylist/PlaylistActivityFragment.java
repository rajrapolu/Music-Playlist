package com.raj.coding.musicplaylist.Activities.ActivityPlaylist;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raj.coding.musicplaylist.DividerRecyclerView;
import com.raj.coding.musicplaylist.R;
import com.raj.coding.musicplaylist.data.MusicPlaylistContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaylistActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int PLAYLIST_LOADER = 1;
    private Unbinder unbinder;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_text_image)
    TextView emptyView;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    @Optional
    @OnClick(R.id.fab)
    public void onFabClick() {
        playlistInterface.displayAddPlaylist();
    }

    private PlaylistAdapter playlistAdapter;
    private PlaylistInterface playlistInterface;

    public interface PlaylistInterface {
        void displayAddPlaylist();
    }

    public PlaylistActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        playlistInterface = ((PlaylistActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_playlist, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        playlistAdapter = new PlaylistAdapter(getContext());
        RecyclerView.ItemDecoration divider;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            divider = new DividerRecyclerView(getResources()
                    .getDrawable(R.drawable.line_divider, null));
        } else {
            divider = new DividerRecyclerView(getResources()
                    .getDrawable(R.drawable.line_divider));
        }
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setAdapter(playlistAdapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        //creating an action in action bar when in sw600dp mode
        if (((PlaylistActivity) getContext()).isTablet()) {
            setHasOptionsMenu(true);
            floatingActionButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.playlist_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_add_playlist) {
            playlistInterface.displayAddPlaylist();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //initializing the loader
        getActivity().getSupportLoaderManager().initLoader(PLAYLIST_LOADER, null, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MusicPlaylistContract.PlaylistEntry._ID,
                MusicPlaylistContract.PlaylistEntry.PLAYLIST_NAME
        };
        return new CursorLoader(getActivity(), MusicPlaylistContract.PlaylistEntry.CONTENT_URI,
                projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() <= 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
        playlistAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        playlistAdapter.swapCursor(null);
    }
}
