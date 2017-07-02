package com.raj.coding.musicplaylist.Activities.ActivityPlaylist;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raj.coding.musicplaylist.R;
import com.raj.coding.musicplaylist.data.MusicPlaylistContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    private final Context mContext;
    private Cursor mCursor;
    private View playlistItem;

    public PlaylistAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void swapCursor(Cursor cursor) {
        if (cursor != null) {
            mCursor = cursor;
            notifyDataSetChanged();
        }
    }

    @Override
    public PlaylistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.playlist_name, parent,
                false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        holder.mPlaylistName.setText(mCursor.getString(mCursor
                .getColumnIndex(MusicPlaylistContract.PlaylistEntry.PLAYLIST_NAME)));

        //handling the click event on an item
        playlistItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCursor.moveToPosition(holder.getAdapterPosition());

                Uri uri = ContentUris.withAppendedId(MusicPlaylistContract.PlaylistEntry.CONTENT_URI,
                        Long.parseLong(mCursor.getString(mCursor
                                .getColumnIndex(MusicPlaylistContract.PlaylistEntry._ID))));

                //calling method to display songs in the playlist
                ((PlaylistActivity) mContext).displaySongs(uri, mCursor.getString(mCursor
                        .getColumnIndex(MusicPlaylistContract.PlaylistEntry.PLAYLIST_NAME)));

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_playlist_name)
        TextView mPlaylistName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            playlistItem = itemView;
        }
    }
}
