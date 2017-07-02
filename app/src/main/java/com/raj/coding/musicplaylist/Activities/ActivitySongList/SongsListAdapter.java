package com.raj.coding.musicplaylist.Activities.ActivitySongList;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.raj.coding.musicplaylist.R;
import com.raj.coding.musicplaylist.data.MusicPlaylistContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.ViewHolder> {

    private final Context mContext;
    private Cursor mCursor;
    private View mSongView;

    public SongsListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void swapCursor(Cursor cursor) {
        if (cursor != null) {
            mCursor = cursor;
            notifyDataSetChanged();
        }
    }

    @Override
    public SongsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.song_item, parent,
                false));
    }

    @Override
    public void onBindViewHolder(final SongsListAdapter.ViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        try {
            holder.mSongName.setText(mCursor.getString(mCursor
                    .getColumnIndex(MusicPlaylistContract.SongEntry.SONG_NAME)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //setting a click listener on delete image
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCursor.moveToPosition(holder.getAdapterPosition())) {
                    String selection = MusicPlaylistContract.SongEntry.SONG_ID + "=?";

                    //deleting the song from database
                    mContext.getContentResolver().delete(
                            MusicPlaylistContract.SongEntry.CONTENT_URI,
                            selection,
                            new String[]{mCursor.getString(mCursor.getColumnIndex
                                    (MusicPlaylistContract.SongEntry.SONG_ID))});
                }

            }
        });

        mSongView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCursor.moveToPosition(holder.getAdapterPosition())) {
                    Toast.makeText(mContext, mContext.getString(R.string.playing) + " " +
                            mCursor.getString(mCursor.getColumnIndex
                                    (MusicPlaylistContract.SongEntry.SONG_NAME)),
                            Toast.LENGTH_SHORT).show();
                }
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
        @BindView(R.id.tv_song_name)
        TextView mSongName;

        @BindView(R.id.image_delete)
        ImageView mDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mSongView = itemView;
        }
    }
}
