package com.imi.dolphin.dolphinchat.adapter.viewholder;

import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.imi.dolphin.dolphinchat.utils.PlaybackStateListener;
import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;

public class ChatReceivedVideoViewHolder extends RecyclerView.ViewHolder {
    private final PlayerView videoReceiveMessage;
    private final TextView tvVideoTime;
    public ChatReceivedVideoViewHolder(@NonNull View itemView) {
        super(itemView);
        videoReceiveMessage = itemView.findViewById(R.id.video_received_message);
        tvVideoTime = itemView.findViewById(R.id.tv_video_received_time);
    }

    public void bindItem(DolphinChat dolphinChat){
        PlaybackStateListener playbackStateListener = new PlaybackStateListener();
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        trackSelector.setParameters(
                trackSelector.buildUponParameters().setMaxVideoSizeSd());
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(itemView.getContext(), trackSelector);
        videoReceiveMessage.hideController();
        videoReceiveMessage.setPlayer(player);
        Uri uri = Uri.parse(dolphinChat.getMediaLink());
        MediaSource mediaSource = buildMediaSource(uri);

        player.addListener(playbackStateListener);
        player.prepare(mediaSource, false, false);

        tvVideoTime.setText(String.valueOf(dolphinChat.getCreatedAt()));

    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultHttpDataSourceFactory("dolphins");

        return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
    }
}
