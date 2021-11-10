package com.imi.dolphin.dolphinchat.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;

public class ChatSendImageViewHolder extends RecyclerView.ViewHolder {
    private final ImageView imgSendMessage;
    private final ImageView imgIndicator;
    private final TextView tvImageTime;
    private final TextView tvImageUploading;
    public ChatSendImageViewHolder(@NonNull View itemView) {
        super(itemView);
        imgSendMessage = itemView.findViewById(R.id.img_send_message);
        tvImageTime = itemView.findViewById(R.id.tv_img_send_time);
        tvImageUploading = itemView.findViewById(R.id.tv_image_uploading);
        imgIndicator = itemView.findViewById(R.id.img_message_indicator);
    }

    public void bindItem(DolphinChat dolphinChat){
        if (dolphinChat.getState().equals(DolphinChat.State.SENT))
            Glide.with(itemView.getContext()).load(R.drawable.ic_check_blue).into(imgIndicator);
        else if (dolphinChat.getState().equals(DolphinChat.State.READ))
            Glide.with(itemView.getContext()).load(R.drawable.ic_check_green).into(imgIndicator);
        else
            Glide.with(itemView.getContext()).load(R.drawable.ic_check_grey).into(imgIndicator);

        Glide.with(itemView.getContext()).load(dolphinChat.getMediaLink()).into(imgSendMessage);
        tvImageTime.setText(String.valueOf(dolphinChat.getCreatedAt()));
    }
}
