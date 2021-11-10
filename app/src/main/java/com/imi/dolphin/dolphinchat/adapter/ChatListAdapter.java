package com.imi.dolphin.dolphinchat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinchat.adapter.viewholder.ChatReceivedAudioViewHolder;
import com.imi.dolphin.dolphinchat.adapter.viewholder.ChatCarouselViewHolder;
import com.imi.dolphin.dolphinchat.adapter.viewholder.ChatReceivedDocumentViewHolder;
import com.imi.dolphin.dolphinchat.adapter.viewholder.ChatReceivedImageViewHolder;
import com.imi.dolphin.dolphinchat.adapter.viewholder.ChatQuickReplyViewHolder;
import com.imi.dolphin.dolphinchat.adapter.viewholder.ChatReceiveViewHolder;
import com.imi.dolphin.dolphinchat.adapter.viewholder.ChatSendAudioViewHolder;
import com.imi.dolphin.dolphinchat.adapter.viewholder.ChatSendDocumentViewHolder;
import com.imi.dolphin.dolphinchat.adapter.viewholder.ChatSendImageViewHolder;
import com.imi.dolphin.dolphinchat.adapter.viewholder.ChatSendVideoViewHolder;
import com.imi.dolphin.dolphinchat.adapter.viewholder.ChatSendViewHolder;
import com.imi.dolphin.dolphinchat.adapter.viewholder.ChatReceivedVideoViewHolder;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;

import java.util.ArrayList;
import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DolphinChat> dolphinChatList = new ArrayList<>();
    private final OnItemClickListener listener;

    private static final int RECEIVED_TEXT = 1;
    private static final int QUICK_REPLY = 2;
    private static final int CAROUSEL = 3;
    private static final int RECEIVED_IMAGE = 4;
    private static final int RECEIVED_VIDEO = 5;
    private static final int RECEIVED_AUDIO = 6;
    private static final int RECEIVED_DOCUMENT = 11;
    private static final int SEND_TEXT = 7;
    private static final int SEND_IMAGE = 8;
    private static final int SEND_VIDEO = 9;
    private static final int SEND_AUDIO = 10;
    private static final int SEND_DOCUMENT = 12;

    public ChatListAdapter(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setData(List<DolphinChat> dolphinChatList){
        this.dolphinChatList = dolphinChatList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (!dolphinChatList.get(position).isAnswer()){
            return getOutgoingMessageType(dolphinChatList.get(position).getType());
        } else if (dolphinChatList.get(position).isAnswer()){
            return getIncomingMessageType(dolphinChatList.get(position).getType());
        } else {
            throw new IllegalStateException("Unexpected value");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case SEND_TEXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_send, parent, false);
                return new ChatSendViewHolder(view);
            case SEND_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send_image, parent, false);
                return new ChatSendImageViewHolder(view);
            case SEND_VIDEO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send_video, parent, false);
                return new ChatSendVideoViewHolder(view);
            case SEND_AUDIO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send_audio, parent, false);
                return new ChatSendAudioViewHolder(view);
            case SEND_DOCUMENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_send_document, parent, false);
                return new ChatSendDocumentViewHolder(view);
            case RECEIVED_TEXT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_received, parent, false);
                return new ChatReceiveViewHolder(view);
            case RECEIVED_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receive_image, parent, false);
                return new ChatReceivedImageViewHolder(view);
            case RECEIVED_VIDEO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receive_video, parent, false);
                return new ChatReceivedVideoViewHolder(view);
            case RECEIVED_AUDIO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receive_audio, parent, false);
                return new ChatReceivedAudioViewHolder(view);
            case RECEIVED_DOCUMENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receive_document, parent, false);
                return new ChatReceivedDocumentViewHolder(view);
            case QUICK_REPLY:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_media, parent, false);
                return new ChatQuickReplyViewHolder(view);
            case CAROUSEL:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_media, parent, false);
                return new ChatCarouselViewHolder(view);
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ChatSendViewHolder)
            ((ChatSendViewHolder) holder).bindItem(dolphinChatList.get(position));
        else if (holder instanceof ChatReceiveViewHolder)
            ((ChatReceiveViewHolder) holder).bindItem(dolphinChatList.get(position));
        else if (holder instanceof ChatQuickReplyViewHolder)
            ((ChatQuickReplyViewHolder) holder).bindItem(dolphinChatList.get(position), listener);
        else if (holder instanceof ChatCarouselViewHolder)
            ((ChatCarouselViewHolder) holder).bindItem(dolphinChatList.get(position), listener);
        else if (holder instanceof ChatReceivedImageViewHolder)
            ((ChatReceivedImageViewHolder) holder).bindItem(dolphinChatList.get(position));
        else if (holder instanceof ChatReceivedVideoViewHolder)
            ((ChatReceivedVideoViewHolder) holder).bindItem(dolphinChatList.get(position));
        else if (holder instanceof ChatReceivedAudioViewHolder)
            ((ChatReceivedAudioViewHolder) holder).bindItem(dolphinChatList.get(position));
        else if (holder instanceof ChatReceivedDocumentViewHolder)
            ((ChatReceivedDocumentViewHolder) holder).bindItem(dolphinChatList.get(position));
        else if (holder instanceof ChatSendImageViewHolder)
            ((ChatSendImageViewHolder) holder).bindItem(dolphinChatList.get(position));
        else if (holder instanceof ChatSendVideoViewHolder)
            ((ChatSendVideoViewHolder) holder).bindItem(dolphinChatList.get(position));
        else if (holder instanceof ChatSendAudioViewHolder)
            ((ChatSendAudioViewHolder) holder).bindItem(dolphinChatList.get(position));
        else if (holder instanceof ChatSendDocumentViewHolder)
            ((ChatSendDocumentViewHolder) holder).bindItem(dolphinChatList.get(position));
        else throw new IllegalStateException("Unexpected value");
    }

    @Override
    public int getItemCount() {
        return dolphinChatList.size();
    }

    private int getOutgoingMessageType(DolphinChat.Type type){
        if (type.equals(DolphinChat.Type.TEXT)){
            return SEND_TEXT;
        } else if (type.equals(DolphinChat.Type.IMAGE)){
            return SEND_IMAGE;
        } else if (type.equals(DolphinChat.Type.VIDEO)){
            return SEND_VIDEO;
        } else if (type.equals(DolphinChat.Type.AUDIO)){
            return SEND_AUDIO;
        } else if (type.equals(DolphinChat.Type.DOCUMENT)){
            return SEND_DOCUMENT;
        } else {
            throw new IllegalStateException("Unexpected value");
        }
    }

    private int getIncomingMessageType(DolphinChat.Type type){
        if (type.equals(DolphinChat.Type.TEXT)){
            return RECEIVED_TEXT;
        } else if (type.equals(DolphinChat.Type.IMAGE)){
            return RECEIVED_IMAGE;
        } else if (type.equals(DolphinChat.Type.VIDEO)){
            return RECEIVED_VIDEO;
        } else if (type.equals(DolphinChat.Type.AUDIO)){
            return RECEIVED_AUDIO;
        } else if (type.equals(DolphinChat.Type.DOCUMENT)){
            return RECEIVED_DOCUMENT;
        } else if (type.equals(DolphinChat.Type.BUTTON_CAROUSEL)){
            return CAROUSEL;
        } else if (type.equals(DolphinChat.Type.QUICK_REPLY)){
            return QUICK_REPLY;
        } else {
            throw new IllegalStateException("Unexpected value");
        }
    }
}
