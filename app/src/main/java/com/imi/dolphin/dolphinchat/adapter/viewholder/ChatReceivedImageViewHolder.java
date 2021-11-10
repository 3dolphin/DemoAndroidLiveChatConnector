package com.imi.dolphin.dolphinchat.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;

public class ChatReceivedImageViewHolder extends RecyclerView.ViewHolder {
    private final ImageView imgReceiveMessage;
    private final TextView tvImageTime;
    public ChatReceivedImageViewHolder(@NonNull View itemView) {
        super(itemView);
        imgReceiveMessage = itemView.findViewById(R.id.img_received_message);
        tvImageTime = itemView.findViewById(R.id.tv_img_received_time);
    }

    public void bindItem(DolphinChat dolphinChat){
        Glide.with(itemView.getContext()).load(dolphinChat.getMediaLink()).into(imgReceiveMessage);
        tvImageTime.setText(String.valueOf(dolphinChat.getCreatedAt()));
    }
}
