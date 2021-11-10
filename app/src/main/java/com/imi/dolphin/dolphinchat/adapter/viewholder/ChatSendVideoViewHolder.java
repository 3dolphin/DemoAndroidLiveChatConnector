package com.imi.dolphin.dolphinchat.adapter.viewholder;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.imi.dolphin.dolphinchat.utils.PlaybackStateListener;
import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinchat.utils.Utils;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;

public class ChatSendVideoViewHolder extends RecyclerView.ViewHolder {
    private final PlayerView videoSendMessage;
    private final TextView tvVideoTime;
    private final TextView tvVideoUploading;
    private final ImageView imgIndicator;
    public ChatSendVideoViewHolder(@NonNull View itemView) {
        super(itemView);
        videoSendMessage = itemView.findViewById(R.id.video_send_message);
        tvVideoTime = itemView.findViewById(R.id.tv_video_send_time);
        tvVideoUploading = itemView.findViewById(R.id.tv_image_uploading);
        imgIndicator = itemView.findViewById(R.id.img_message_indicator);
    }

    public void bindItem(DolphinChat dolphinChat){
        if (dolphinChat.getState().equals(DolphinChat.State.SENT))
            Glide.with(itemView.getContext()).load(R.drawable.ic_check_blue).into(imgIndicator);
        else if (dolphinChat.getState().equals(DolphinChat.State.READ))
            Glide.with(itemView.getContext()).load(R.drawable.ic_check_green).into(imgIndicator);
        else
            Glide.with(itemView.getContext()).load(R.drawable.ic_check_grey).into(imgIndicator);

        PlaybackStateListener playbackStateListener = new PlaybackStateListener();
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        trackSelector.setParameters(
                trackSelector.buildUponParameters().setMaxVideoSizeSd());
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(itemView.getContext(), trackSelector);
        videoSendMessage.hideController();
        videoSendMessage.setPlayer(player);
        Uri uri = Uri.parse(dolphinChat.getMediaLink());
        MediaSource mediaSource = Utils.buildMediaSource(uri, itemView.getContext());

        player.addListener(playbackStateListener);
        player.prepare(mediaSource, false, false);

        tvVideoTime.setText(String.valueOf(dolphinChat.getCreatedAt()));

    }
}
