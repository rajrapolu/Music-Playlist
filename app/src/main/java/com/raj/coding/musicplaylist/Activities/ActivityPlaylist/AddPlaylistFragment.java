package com.raj.coding.musicplaylist.Activities.ActivityPlaylist;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.raj.coding.musicplaylist.R;
import com.raj.coding.musicplaylist.data.MusicPlaylistContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddPlaylistFragment extends DialogFragment {

    private Unbinder unbinder;

    @BindView(R.id.tv_add_playlist_title)
    TextView mAddPlayListTitle;

    @BindView(R.id.edit_playlist_name)
    EditText mPlaylistName;

    @OnClick(R.id.button_add)
    public void onAddButtonClick() {
        if (!mPlaylistName.getText().toString().isEmpty()) {
            insertPlaylist();
        } else {
            Toast.makeText(getContext(), R.string.playlist_name_mandatory, Toast.LENGTH_SHORT).show();
        }
    }

    //inserting playlist in to the database
    private void insertPlaylist() {
        ContentValues values = new ContentValues();
        values.put(MusicPlaylistContract.PlaylistEntry.PLAYLIST_NAME, mPlaylistName.getText()
                .toString());

        Uri uri = getContext().getContentResolver()
                .insert(MusicPlaylistContract.PlaylistEntry.CONTENT_URI, values);
        if (uri == null) {
            Toast.makeText(getContext(), R.string.playlist_already_exists,
                    Toast.LENGTH_LONG).show();
        } else {
            AddPlaylistFragment.this.dismiss();
        }
    }

    @OnClick(R.id.button_cancel)
    public void onCancelButtonClicked() {
        AddPlaylistFragment.this.dismiss();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_playlist, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
