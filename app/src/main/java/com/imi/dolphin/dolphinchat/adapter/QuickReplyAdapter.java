package com.imi.dolphin.dolphinchat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;
import com.imi.dolphin.dolphinlib.data.model.DolphinQuickReply;

import java.util.List;

public class QuickReplyAdapter extends RecyclerView.Adapter<QuickReplyAdapter.QuickReplyViewHolder> {

    private List<DolphinQuickReply> quickReplies;
    private OnItemClickListener listener;

    public void setData(List<DolphinQuickReply> quickReplies, OnItemClickListener listener){
        this.quickReplies = quickReplies;
        this.listener = listener;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuickReplyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuickReplyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quick_reply, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuickReplyViewHolder holder, int position) {
        holder.bindItem(quickReplies.get(position));
    }

    @Override
    public int getItemCount() {
        return quickReplies.size();
    }

    public class QuickReplyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        public QuickReplyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_quick_reply);
        }

        void bindItem(DolphinQuickReply dolphinQuickReply){
            tvTitle.setText(dolphinQuickReply.getLabel());
            tvTitle.setOnClickListener(v ->
                    listener.onItemClick(dolphinQuickReply.getValue(), DolphinChat.Type.QUICK_REPLY));
        }
    }
}
