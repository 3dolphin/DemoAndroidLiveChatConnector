package com.imi.dolphin.dolphinchat.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinchat.adapter.ButtonCarouselAdapter;
import com.imi.dolphin.dolphinchat.adapter.OnItemClickListener;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;

public class ChatCarouselViewHolder extends RecyclerView.ViewHolder {
    private final RecyclerView rvButtonCarousel;
    private final TextView tvCarouselTime;
    private final TextView tvTitleMedia;

    public ChatCarouselViewHolder(@NonNull View itemView) {
        super(itemView);
        rvButtonCarousel = itemView.findViewById(R.id.rv_received_message);
        tvCarouselTime = itemView.findViewById(R.id.tv_media_received_time);
        tvTitleMedia = itemView.findViewById(R.id.tv_title_media);
    }

    public void bindItem(DolphinChat dolphinChat, OnItemClickListener listener){
        if (dolphinChat.getText() != null && dolphinChat.getText().isEmpty()) {
            tvTitleMedia.setText(dolphinChat.getText());
            tvTitleMedia.setVisibility(View.VISIBLE);
        } else {
            tvTitleMedia.setVisibility(View.GONE);
        }
        tvCarouselTime.setText(String.valueOf(dolphinChat.getCreatedAt()));

        rvButtonCarousel.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));

        ButtonCarouselAdapter carouselAdapter = new ButtonCarouselAdapter();
        carouselAdapter.setData(dolphinChat.getDolphinCarousels(), listener);
        rvButtonCarousel.setAdapter(carouselAdapter);
    }
}
