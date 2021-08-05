# SAN Android Document

## Get Started with SAN SDK

Welcome to SAN Ad! This navigation will provide the comprehensive guidelines including the SDK integration document, SAN platform's configuration guides, advanced features and classic monetization cases, which will navigate you to use SAN SDK quickly and maximize your monetization.

## Integrate the SAN SDK for Android

**Prerequisites**

- Use Android Studio 3.2 or higher
- Support androidX
- minSdkVersion 16 or higher
- compileSdkVersion 28 or higher

### Step 1. Download the SAN Android SDK

The SAN Open SDK is available as an AAR via mavenCentral.To add the san-sdk dependency, open your project and update the app module’s `build.gradle` to have the following `repositories` and `dependencies`:

```
repositories {
    // ... other project repositories
    mavenCentral()
}
//...

dependencies {
    // ... other project dependencies
    api "com.myadsget:san-sdk:1.1.0.+"// SAN sdk
}
```

To support Java 8, add the language feature support:

```
android {
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

### Step 2.Update Your Android Manifest

Update your `AndroidManifest.xml` in order to complete the SDK integration. Add the following permissions and activity declarations according to the bundle you are integrating.

1. Declare the following permissions:

```
<!-- Required permissions -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- Optional permissions. Will pass Lat/Lon values when available. Choose either Coarse or Fine -->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
```

2. Add the AppKey in meta-data in the example below

```
    <manifest>
       <application>
            <meta-data android:name="com.san.APP_KEY"
                android:value="YOUR_APP_KEY"/>
       </application>
    </manifest> 
```

### Step 3：Add network_security_config.xml

Oct 01, 2020 - Android 9.0 (API 28) blocks cleartext (non-HTTPS) traffic by default, which can prevent ads from serving correctly. To mitigate that, publishers whose apps run on Android 9.0 or above should ensure to add a network security config file. Doing so allowlists cleartext traffic and allows non-HTTPS ads to serve.

1. In your AndroidManifest.xml file, add the following:

   ```
    <manifest>
        <application
            ...
            android:networkSecurityConfig="@xml/network_security_config"
            ...>
        </application>
    </manifest>
   ```

2. In your `network_security_config.xml` file, add a `base-config` that sets `cleartextTrafficPermitted` to `true`:

   ```
    <?xml version="1.0" encoding="utf-8"?>
     <network-security-config>
        ...
        <base-config cleartextTrafficPermitted="true">
            <trust-anchors>
                <certificates src="system"/>
            </trust-anchors>
        </base-config>
        ...
    </network-security-config>
   ```

### Step 4. Configure Ad Placement in Your App

You should already have created an ad placement on SAN’s site and received an Ad Placement ID. You’ll use it now to identify that ad placementId in your app and request ads from SAN that are relevant for your users.

Once you’ve completed the above steps, you can start displaying ads in your application by configuring the PlacementIds as shown in the link below for your ad format:

- [Banner](https://github.com/san-sdk/sample#banner-ads)
- [Native](https://github.com/san-sdk/sample#native-ads)
- [Interstitial](https://github.com/san-sdk/sample#interstitial-ads)
- [Rewarded Video](https://github.com/san-sdk/sample#rewarded-video-ads)



## Initialize

After you have integrated the SAN SDK and created an ad placement, you must call `SanAdSdk.init()` **before you send any ad requests**. Initialization is **required** for a number of new functionalities:

It is recommended to initialize in `Application onCreate()`

```
public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        SanAdSdk.init(this);
    }
}
```



## Banner Ads

Banner ads usually appear at the top or bottom of your app’s screen.

###  Prerequisites

Before integrating banner ads in your app:

1. Already has a **PlacementId** using the format ‘Banner’.
2. Follow our steps to [Integrate the SAN SDK for Android](https://github.com/san-sdk/sample#integrate-the-san-sdk-for-android) into your project.
3. Integrated the SAN Ad open SDK

### Loading Banner Ads in Your App

#### Step 1. Load an Ad Into the Banner Slot

Next, declare an instance variable for your `SANBanner`：

```
private SANBanner banner;
```

Set your `SANBanner` placementId, then call `load()` to fetch and load the ad:

```
banner = new SANBanner(context, placementId);
banner.setAdSize(AdSize.BANNER);
banner.setAdLoadListener(new IAdListener.AdLoadListener() {
    @Override
    public void onAdLoaded(SANAd adWrapper) {
        //the banner has successfully retrieved an ad.
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
```

#### Step 2. Show Banner Ad

You can get the banner view and add it to your container view by using the getAdView method, when the onAdLoaded method is called

```
banner.getAdView();
```

### Ad Preload

Using the `preload()` to preload in advance reduces the load time at presentation time

```
banner.preload();
```



## Native Ads

Native ads let you monetize your app in a way that’s consistent with its existing design.  You can design the ad layout to be consistent with the look and feel of your app. The SDK automatically handles image caching and metrics tracking so you can focus on how, when, and where to display ads.

### Prerequisites

Before integrating native ads into your app:

1. Already has a **PlacementId** using the format ‘Native’.
2. Follow our steps to [Integrate the SAN SDK for Android](https://github.com/san-sdk/sample#integrate-the-san-sdk-for-android) into your project.
3. Integrated the SAN Ad open SDK

####  Step 1. Request the Native Ad

```
SANNativeAd sanNative = new SANNativeAd(context,  placementId); 
sanNative.setAdLoadListener(new IAdListener.AdLoadListener() {
    @Override
    public void onAdLoaded(SANAd adWrapper) {
        // Called when the ad for the given placementId has loaded.
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
```

#### Step 2：Create an XML layout

The Sample:

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/native_outer_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@android:color/white">

    <ImageView
        android:id="@+id/native_icon_image"
        android:layout_width="64dp"
        android:layout_height="64dp"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:layout_marginStart="10dp"
        android:layout_marginTop="10dp"
        android:background="@null"
        android:contentDescription="@null" />

    <TextView
        android:id="@+id/native_title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_toEndOf="@+id/native_icon_image"
        android:layout_alignParentTop="true"
        android:layout_marginTop="32dp"
        android:layout_marginStart="8dp"
        android:textColor="@android:color/darker_gray"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/native_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@+id/native_icon_image"
        android:layout_alignParentStart="true"
        android:layout_marginStart="10dp"
        android:layout_marginTop="10dp"
        android:textColor="@android:color/darker_gray" />

    <com.san.ads.MediaView
        android:id="@+id/native_main_image"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:layout_below="@+id/native_text"
        android:layout_alignParentStart="true"
        android:layout_marginStart="10dp"
        android:layout_marginTop="10dp"
        android:layout_marginEnd="10dp"
        android:background="@null"
        android:scaleType="centerCrop" />

    <Button
        android:id="@+id/native_cta"
        android:layout_width="70dp"
        android:layout_height="wrap_content"
        android:layout_alignParentEnd="true"
        android:layout_marginTop="10dp"
        android:layout_marginEnd="10dp"
        android:background="@drawable/ads_button_bg"
        android:clickable="true"
        android:focusable="true"
        android:singleLine="true"
        android:textColor="@color/white"
        android:textStyle="bold" />

    <FrameLayout
        android:id="@+id/ad_choices"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentEnd="true"
        android:layout_alignParentTop="true"/>
        
    <ImageView
        android:id="@+id/feed_ad_badge"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/ad_badge" />
</RelativeLayout>

```

![NateAdSample](https://tva1.sinaimg.cn/large/008eGmZEgy1gmgczlwc9nj30ex0i975z.jpg)



#### Step 3：Show Native Ads

- Get BaseNativeAd to show

```
  BaseNativeAd nativeAd;//The BaseNativeAd is obtained from void onAdLoaded(SANAd adWrapper)
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
```
- Ad attribution

You must display an ad attribution to denote that the view is an advertisement

- MediaView

MediaView ratio, we recommend using 16:9, a large part of the native advertising image size is this ratio

- Use nativeAd.prepare()

Make sure to use the prepare method, this method will monitor the contentView and if it is obscured, or not visible, it will be considered invalid for display.

### Ad Preload

Using the `preload()` to preload in advance reduces the load time at presentation time

```
nativeAd.preload();
```



## Interstitial Ads

Interstitial ads provide full-screen experiences, commonly incorporating rich media to offer a higher level of interactivity compared to banner ads. Interstitials are typically shown during natural transitions in your app; for example, after completing a game level, or while waiting for a new view to load. Use the `SANInterstitial` object and its associated listeners to fetch and display interstitial ads in your app.

###  Prerequisites

1. Already has a **PlacementId** using the format ‘Interstitial’.
2. Follow our steps to [Integrate the SAN SDK for Android](https://github.com/san-sdk/sample#integrate-the-san-sdk-for-android) into your project.
3. Integrated the SAN Ad open SDK

### Load Interstitial Ads in Your App

#### Step 1. Create an Interstitial Ad

```
SANInterstitial interstitial = new SANInterstitial(context, placementId);
interstitial.setAdLoadListener(new IAdListener.AdLoadListener() {
    @Override
    public void onAdLoaded(SANAd adWrapper) {
        // Called when the ad for the given placementId has loaded and is ready to be shown.
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
```

#### Step 2. Display an Interstitial Ad

If `isAdReady()` returns true, display the interstitial by calling the `show()` method

```
if (interstitial.isAdReady()){
    interstitial.show();
}
```

#### Step 3：Destory

When the interstitial Ad dismissed use the `destroy()`

```
interstitial.destroy();
```

### Ad Preload

Using the `preload()` to preload in advance reduces the load time at presentation time

```
interstitial.preload();
```



## Rewarded Video Ads

Rewarded video ads are a great way to keep users engaged in your app while earning ad revenue. The reward generally comes in the form of in-game currency (gold, coins, power-ups, etc.) and is distributed to the user after a successful video completion.

###  Prerequisites

1. Already has a **PlacementId** using the format ‘Rewarded Video’.
2. Follow our steps to [Integrate the SAN SDK for Android](https://github.com/san-sdk/sample#integrate-the-san-sdk-for-android) into your project.
3. Integrated the SAN Ad open SDK

### Basic Integration

####  Step 1： Request and Cache the Rewarded Video

```
SANReward rewardedAd = new SANReward(context, placementId);
rewardedAd.setAdLoadListener(new IAdListener.AdLoadListener() {
    @Override
    public void onAdLoaded(SANAd adWrapper) {
        // Called when the video for the given placementId has loaded.
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
```

#### Step 2：Display an Rewarded Ad

If `isAdReady()` returns true, display the interstitial by calling the `show()` method

```
if (rewardedAd.isAdReady()) {
    rewardedAd.show();
}
```

#### Step 3：Destory

When the Rewarded Ad dismissed use the `destroy()`

```
rewardedAd.destroy();
```

### Ad Preload

Using the `preload()` to preload in advance reduces the load time at presentation time

```
rewardedAd.preload();
```
 