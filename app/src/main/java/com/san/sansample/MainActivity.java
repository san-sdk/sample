package com.san.sansample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.san.ads.AdError;
import com.san.ads.AdSize;
import com.san.ads.MediaView;
import com.san.ads.SANBanner;
import com.san.ads.SANInterstitial;
import com.san.ads.SANNativeAd;
import com.san.ads.SANReward;
import com.san.ads.base.IAdListener;
import com.san.ads.core.SANAd;
import com.san.ads.render.AdViewRenderHelper;
import com.san.ads.render.SANNativeAdRenderer;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String ID_INTERSTITIAL = "1752";
    private static final String ID_REWARDED = "1753";
    private static final String ID_BANNER = "1754";
    private static final String ID_BANNER250 = "1751";
    private static final String ID_NATIVE = "1769";

    private ViewGroup mBannerContainer;
    private ViewGroup mNativeContainer;

    private SANInterstitial interstitial;
    private SANReward rewardedAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBannerContainer = findViewById(R.id.banner_container);
        mNativeContainer = findViewById(R.id.native_render_container);
        Button interstitialButton = findViewById(R.id.btn_request_itl);
        interstitialButton.setOnClickListener(view -> requestInterstitialAd(ID_INTERSTITIAL));

        Button rewardedButton = findViewById(R.id.btn_request_rwd);
        rewardedButton.setOnClickListener(view -> requestRewardedAd(ID_REWARDED));

        Button bannerButton = findViewById(R.id.btn_request_banner);
        bannerButton.setOnClickListener(view -> requestBannerAd(AdSize.BANNER, ID_BANNER));

        Button banner250Button = findViewById(R.id.btn_request_banner250);
        banner250Button.setOnClickListener(view -> requestBannerAd(AdSize.MEDIUM_RECTANGLE, ID_BANNER250));

        Button nativeButton = findViewById(R.id.btn_native);
        nativeButton.setOnClickListener(view -> requestNativeAd(ID_NATIVE));
    }

    private void requestInterstitialAd(String placementId) {
        interstitial = new SANInterstitial(this, placementId);
        interstitial.setAdLoadListener(new IAdListener.AdLoadListener() {
            @Override
            public void onAdLoaded(SANAd adWrapper) {
                // Called when the ad for the given placementId has loaded and is ready to be shown.
                if (interstitial.isAdReady()) {
                    interstitial.show();
                }
            }

            @Override
            public void onAdLoadError(AdError adError) {
                // Called when a ad fails to load for the given placementId.
            }
        });
        interstitial.setAdActionListener(new IAdListener.AdActionListener() {
            @Override
            public void onAdImpressionError(AdError error) {
                // Called when a ad show failed.
            }

            @Override
            public void onAdImpression() {
                // Called when a ad shown
            }

            @Override
            public void onAdClicked() {
                // Called when interstitial is clicked
            }

            @Override
            public void onAdCompleted() {

            }

            @Override
            public void onAdClosed(boolean hasRewarded) {
                // Called when a interstitial ad is closed.
            }
        });
        interstitial.load();//Request Ad
    }

    private void requestRewardedAd(String placementId) {
        rewardedAd = new SANReward(this, placementId);
        rewardedAd.setAdLoadListener(new IAdListener.AdLoadListener() {
            @Override
            public void onAdLoaded(SANAd adWrapper) {
                // Called when the video for the given placementId has loaded.
                if (rewardedAd.isAdReady()) {
                    rewardedAd.show();
                }
            }

            @Override
            public void onAdLoadError(AdError adError) {
                // Called when a video fails to load for the given placementId.
            }
        });
        rewardedAd.setAdActionListener(new IAdListener.AdActionListener() {
            @Override
            public void onAdImpressionError(AdError error) {

            }

            @Override
            public void onAdImpression() {
                // Called when a rewarded video starts playing.
            }

            @Override
            public void onAdClicked() {
                //  Called when a rewarded video is clicked.
            }

            @Override
            public void onAdCompleted() {
                // Called when a rewarded video is completed and the user should be rewarded.
            }

            @Override
            public void onAdClosed(boolean hasRewarded) {
                // Called when a rewarded video is closed.
            }
        });
        rewardedAd.load();//Request ad
    }

    private void requestBannerAd(AdSize bannerSize, String placementId) {
        SANBanner banner = new SANBanner(this, placementId);
        banner.setAdSize(bannerSize);
        banner.setAdLoadListener(new IAdListener.AdLoadListener() {
            @Override
            public void onAdLoaded(SANAd adWrapper) {
                //the banner has successfully retrieved an ad.
                mBannerContainer.removeAllViews();
                mBannerContainer.addView(banner.getAdView());
            }

            @Override
            public void onAdLoadError(AdError adError) {
                //the banner has failed to retrieve an ad.
            }
        });
        banner.setAdActionListener(new IAdListener.AdActionListener() {
            @Override
            public void onAdImpressionError(AdError error) {

            }

            @Override
            public void onAdImpression() {
                //the banner has showed
            }

            @Override
            public void onAdClicked() {
                //the user has tapped on the banner.
            }

            @Override
            public void onAdCompleted() {

            }

            @Override
            public void onAdClosed(boolean hasRewarded) {

            }
        });
        banner.load();
    }

    private void requestNativeAd(String placementId) {
        SANNativeAd sanNative = new SANNativeAd(this, placementId);
        sanNative.setAdLoadListener(new IAdListener.AdLoadListener() {
            @Override
            public void onAdLoaded(SANAd adWrapper) {
                // Called when the ad for the given placementId has loaded.
                renderNativeAdView(adWrapper);
//                renderNativeAdForMediation(sanNative);
            }

            @Override
            public void onAdLoadError(AdError adError) {
                // Called when a ad fails to load for the given placementId.
            }
        });
        sanNative.setAdActionListener(new IAdListener.AdActionListener() {
            @Override
            public void onAdImpressionError(AdError error) {

            }

            @Override
            public void onAdImpression() {
                // Called when a ad shown
            }

            @Override
            public void onAdClicked() {
                // Called when a ad is clicked
            }

            @Override
            public void onAdCompleted() {

            }

            @Override
            public void onAdClosed(boolean hasRewarded) {

            }
        });
        sanNative.load();//Request ad
    }

    private void renderNativeAdView(SANAd sanNative) {
        SANNativeAd nativeAd = (SANNativeAd) sanNative;
        View contentView = LayoutInflater.from(this).inflate(R.layout.ad_native_layout, mNativeContainer, false);
        TextView titleText = contentView.findViewById(R.id.native_title);
        TextView contentText = contentView.findViewById(R.id.native_text);
        TextView buttonView = contentView.findViewById(R.id.native_button);
        ImageView iconImage = contentView.findViewById(R.id.native_icon_image);
        MediaView mediaLayout = contentView.findViewById(R.id.native_main_image);

        //text
        titleText.setText(nativeAd.getTitle());
        contentText.setText(nativeAd.getContent());
        buttonView.setText(nativeAd.getCallToAction());
        //icon
        AdViewRenderHelper.loadImage(iconImage.getContext(), nativeAd.getIconUrl(), iconImage);
        //media view
        mediaLayout.loadMadsMediaView(nativeAd.getNativeAd());

        //click list
        List<View> clickViews = new ArrayList<>();
        clickViews.add(titleText);
        clickViews.add(contentText);
        clickViews.add(buttonView);
        clickViews.add(iconImage);
        clickViews.add(mediaLayout);
        //prepare
        nativeAd.prepare(contentView, clickViews, null);
        mNativeContainer.removeAllViews();
        mNativeContainer.addView(contentView);
    }

    private void renderNativeAdForMediation(SANNativeAd nativeAd) {
        if (mNativeContainer == null)
            return;
        mNativeContainer.removeAllViews();

        SANNativeAdRenderer.SViewBinder builder = new SANNativeAdRenderer.SViewBinder.Builder(R.layout.ad_native_layout_mediation)
                .iconImageId(R.id.native_icon_image)
                .mainImageId(R.id.native_main_image)
                .titleId(R.id.native_title)
                .textId(R.id.native_text)
                .callToActionId(R.id.native_button)
                .build();
        SANNativeAdRenderer adRenderer = new SANNativeAdRenderer(builder);
        View adView = adRenderer.createAdView(this, nativeAd, null);
        adRenderer.renderAdView(adView, nativeAd);
        mNativeContainer.addView(adView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (interstitial != null)
            interstitial.destroy();
        if (rewardedAd != null)
            rewardedAd.destroy();
    }
}