package com.imi.dolphin.dolphinchat.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;

public class ChatSendDocumentViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvTitle;
    private final TextView tvDocumentTime;
    private final ImageView imgIndicator;

    public ChatSendDocumentViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_document_title);
        tvDocumentTime = itemView.findViewById(R.id.tv_sender_time);
        imgIndicator = itemView.findViewById(R.id.img_message_indicator);
    }

    public void bindItem(DolphinChat dolphinChat) {
        String[] link = dolphinChat.getMediaLink().split("/");
        String[] nameWithAccessToken = link[link.length - 1].split("\\?");
        String filename = nameWithAccessToken[0];

        tvTitle.setText(filename);
        tvDocumentTime.setText(String.valueOf(dolphinChat.getCreatedAt()));

        if (dolphinChat.getState().equals(DolphinChat.State.SENT))
            Glide.with(itemView.getContext()).load(R.drawable.ic_check_blue).into(imgIndicator);
        else if (dolphinChat.getState().equals(DolphinChat.State.READ))
            Glide.with(itemView.getContext()).load(R.drawable.ic_check_green).into(imgIndicator);
        else
            Glide.with(itemView.getContext()).load(R.drawable.ic_check_grey).into(imgIndicator);
    }
}
