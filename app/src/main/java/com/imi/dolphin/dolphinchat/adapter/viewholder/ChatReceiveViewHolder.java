package com.imi.dolphin.dolphinchat.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;

public class ChatReceiveViewHolder extends RecyclerView.ViewHolder {
    private final TextView txtMessage;
    private final TextView txtTime;

    public ChatReceiveViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMessage = itemView.findViewById(R.id.tv_received_message);
        txtTime = itemView.findViewById(R.id.tv_received_time);
    }

    public void bindItem(DolphinChat dolphinChat) {
        txtMessage.setText(dolphinChat.getText());
        txtTime.setText(dolphinChat.getCreatedAt().toString());
    }
}
