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
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.imi.dolphin.dolphinchat.utils.PlaybackStateListener;
import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;

public class ChatSendAudioViewHolder extends RecyclerView.ViewHolder {
    private final PlayerView audioSendMessage;
    private final TextView tvAudioTime;
    private final TextView tvAudioUploading;
    private final ImageView imgIndicator;
    public ChatSendAudioViewHolder(@NonNull View itemView) {
        super(itemView);
        audioSendMessage = itemView.findViewById(R.id.audio_send_message);
        tvAudioTime = itemView.findViewById(R.id.tv_audio_send_time);
        tvAudioUploading = itemView.findViewById(R.id.tv_image_uploading);
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
        audioSendMessage.hideController();
        audioSendMessage.setPlayer(player);
        Uri uri = Uri.parse(dolphinChat.getMediaLink());
        MediaSource mediaSource = buildMediaSource(uri);

        player.addListener(playbackStateListener);
        player.prepare(mediaSource, false, false);

        tvAudioTime.setText(String.valueOf(dolphinChat.getCreatedAt()));
    }

    private MediaSource buildMediaSource(Uri uri) {
        MediaSource audioSource;

        if (uri.toString().startsWith("http://") || uri.toString().startsWith("https://")){
            DataSource.Factory dataSourceFactory =
                    new DefaultHttpDataSourceFactory("dolphins");
            audioSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        } else {
            DataSource.Factory dataSourceFactoryFile = new DefaultDataSourceFactory(
                    itemView.getContext(), Util.getUserAgent(itemView.getContext(), "SongShakes"));
            audioSource = new ExtractorMediaSource.Factory(dataSourceFactoryFile).
                    createMediaSource(uri);
        }
        return audioSource;
    }
}
