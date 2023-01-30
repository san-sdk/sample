package com.san.sansample;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.san.ads.base.INativeAd;
import com.san.ads.render.AdViewRenderHelper;
import com.san.ads.render.SANNativeAdRenderer;

import java.util.ArrayList;
import java.util.List;

public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.AdHolder> {
    private final SANNativeAdRenderer adRenderer;
    private final List<INativeAd> adList = new ArrayList<>();

    public MultiAdapter(SANNativeAdRenderer adRenderer) {
        this.adRenderer = adRenderer;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAdData(List<INativeAd> adList) {
        this.adList.clear();
        if (adList != null && !adList.isEmpty()) {
            this.adList.addAll(adList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdHolder(adRenderer.createAdView(parent.getContext(), null, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull AdHolder holder, int position) {
        if (position >= 0 && position < adList.size())
            holder.bindView(adList.get(position));
    }

    @Override
    public int getItemCount() {
        return adList.size();
    }

    static class AdHolder extends RecyclerView.ViewHolder {
        AdHolder(View view) {
            super(view);
        }

        private final TextView textView = itemView.findViewById(R.id.ad_text);
        private final ImageView adImgView = itemView.findViewById(R.id.ad_image);

        public void bindView(INativeAd nativeAd) {
            textView.setText(nativeAd.getTitle());
            AdViewRenderHelper.loadImage(itemView.getContext(), nativeAd.getIconUrl(), adImgView);

            List<View> clickViews = new ArrayList<>();
            clickViews.add(textView);
            clickViews.add(adImgView);
            // view 参数传图标根View，list 参数 传需要点击的View
            nativeAd.prepare(itemView, clickViews, null);
        }
    }
}
