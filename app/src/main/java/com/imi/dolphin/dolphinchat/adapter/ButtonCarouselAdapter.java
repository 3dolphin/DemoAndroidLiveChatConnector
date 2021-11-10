package com.imi.dolphin.dolphinchat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.imi.dolphin.dolphinchat.R;
import com.imi.dolphin.dolphinlib.data.model.DolphinCarousel;
import com.imi.dolphin.dolphinlib.data.model.DolphinChat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ButtonCarouselAdapter extends RecyclerView.Adapter<ButtonCarouselAdapter.CarouselViewHolder> {

    private List<DolphinCarousel> carousels;
    private OnItemClickListener listener;

    public void setData(List<DolphinCarousel> carousels, OnItemClickListener listener){
        this.carousels = carousels;
        this.listener = listener;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarouselViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button_carousel, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        holder.bindItem(carousels.get(position));
    }

    @Override
    public int getItemCount() {
        return carousels.size();
    }

    public class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBanner;
        TextView tvTitle, tvSubtitle;
        LinearLayout layout;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBanner = itemView.findViewById(R.id.iv_banner_carousel);
            tvTitle = itemView.findViewById(R.id.tv_title_carousel);
            tvSubtitle = itemView.findViewById(R.id.tv_subtitle_carousel);
            layout = itemView.findViewById(R.id.layout_button_carousel);
        }

        void bindItem(DolphinCarousel carousel){
            tvTitle.setText(carousel.getTitle());
            tvSubtitle.setText(carousel.getSubTitle());
            if (carousel.getPictureLink() != null) {
                Glide.with(itemView.getContext()).load(carousel.getPictureLink()).into(imgBanner);
            }
            for (int i = 0; i < carousel.getButtonValues().length(); i++) {
                try {
                    JSONObject obj = carousel.getButtonValues().getJSONObject(i);
                    String value = obj.getString("value");
                    Button btnAction = new Button(itemView.getContext());
                    btnAction.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    btnAction.setText(obj.getString("name"));
                    layout.addView(btnAction);
                    btnAction.setOnClickListener(v -> listener.onItemClick(value, DolphinChat.Type.BUTTON_CAROUSEL));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
