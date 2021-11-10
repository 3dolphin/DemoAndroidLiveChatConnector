package com.imi.dolphin.dolphinchat.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;

public class ChatSendViewHolder extends RecyclerView.ViewHolder {
    private final TextView txtMessage;
    private final TextView txtTime;
    private final ImageView imgIndicator;

    public ChatSendViewHolder(@NonNull View itemView) {
        super(itemView);
        txtMessage = itemView.findViewById(R.id.tv_sender_message);
        txtTime = itemView.findViewById(R.id.tv_sender_time);
        imgIndicator = itemView.findViewById(R.id.img_message_indicator);
    }

    public void bindItem(DolphinChat dolphinChat){
        txtMessage.setText(dolphinChat.getText());
        txtTime.setText(dolphinChat.getCreatedAt().toString());
        if (dolphinChat.getState().equals(DolphinChat.State.SENT))
            Glide.with(itemView.getContext()).load(R.drawable.ic_check_blue).into(imgIndicator);
        else if (dolphinChat.getState().equals(DolphinChat.State.READ))
            Glide.with(itemView.getContext()).load(R.drawable.ic_check_green).into(imgIndicator);
        else
            Glide.with(itemView.getContext()).load(R.drawable.ic_check_grey).into(imgIndicator);
    }
}
