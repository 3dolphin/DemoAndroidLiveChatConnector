package com.imi.dolphin.dolphinchat.adapter;

import com.imi.dolphin.dolphinlib.data.model.DolphinChat;

public interface OnItemClickListener {
    void onItemClick(String value, DolphinChat.Type type);
}
