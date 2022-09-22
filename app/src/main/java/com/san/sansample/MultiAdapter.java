package com.san.sansample;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.san.ads.base.INativeAd;
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
            adRenderer.renderAdView(holder.itemView, adList.get(position));
    }

    @Override
    public int getItemCount() {
        return adList.size();
    }

    static class AdHolder extends RecyclerView.ViewHolder {
        AdHolder(View view) {
            super(view);
        }
    }
}
