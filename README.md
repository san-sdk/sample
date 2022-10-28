# Integrate SAN SDK for Multi-Native ads

Multi-Native ads let you monetize your app in a way that’s consistent with its existing design.  You can design the ad layout to be consistent with the look and feel of your app. The SDK automatically handles image caching and metrics tracking so you can focus on how, when, and where to display ads.

## Use SAN-SDK Prerequisites

- Use Android Studio 3.2 or higher
- Support androidX
- minSdkVersion 16 or higher
- compileSdkVersion 30 or higher

## Integrate
### Step 1. Download the SAN Android SDK

The SAN Open SDK is available as an AAR via mavenCentral.To add the san-sdk dependency, open your project and update the app module’s `build.gradle` to have the following `repositories` and `dependencies`:

```groovy
repositories {
    // ... other project repositories
    mavenCentral()
}
//...

dependencies {
    // ... other project dependencies
    // important: must call SanAdSdk#notifyConsentStatus(true) after the user agree with privacy police. The SDK will collect 'gaid' for ads after you called this method.
    api "com.myadsget:san-sdk:3.13.0.1-ln"// SAN sdk
}
```

To support Java 8, add the language feature support:

```groovy
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

```xml
<!-- Required permissions -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

<!-- Optional permissions. Will pass Lat/Lon values when available. Choose either Coarse or Fine -->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

2. Add `meta-data` and `queries`

```xml
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

```xml
    <manifest>
        <application
            ...
            android:networkSecurityConfig="@xml/network_security_config"
            ...>
        </application>
    </manifest>
```

2. In your `network_security_config.xml` file, add a `base-config` that sets `cleartextTrafficPermitted` to `true`:

```xml
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
## Initialize

After you have integrated the SAN SDK and created an ad placement, you must call `SanAdSdk.init()` **before you send any ad requests**. Initialization is **required** for a number of new functionalities:

It is recommended to initialize in `Application onCreate()` on main process, we do not support multiple processes.

```java
public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (isMainProcess)
            SanAdSdk.init(this);
    }
}
```

## Configure Ad Placement in Your App

You should already have created an ad placement on SAN’s site and received an Ad Placement ID. You’ll use it now to identify that ad placementId in your app and request ads from SAN that are relevant for your users.

Once you’ve completed the above steps, you can start displaying ads in your application by configuring the PlacementIds as shown in the link below for your ad format:

- [Banner](https://github.com/san-sdk/sample#Banner-Ads)
- [Native](https://github.com/san-sdk/sample#Native-Ads)
- [Interstitial](https://github.com/san-sdk/sample#Interstitial-Ads)
- [Rewarded Video](https://github.com/san-sdk/sample#Rewarded-Video-Ads)


## Use Multi-Native Ad

### Prerequisites
Before integrating native ads into your app:

1. Already has a **PlacementId** using the format `Multi-Native`.

###  Step 1. Request the Native Ad

```java
SANMultiNativeAd multiNativeAd = new SANMultiNativeAd(this, placementId);
multiNativeAd.setAdLoadListener(new IAdListener.AdLoadListener<SANMultiNativeAd>() {
    @Override
    public void onAdLoaded(SANMultiNativeAd sanAd) {
        // Called when the ad for the given placementId has loaded.
    }

    @Override
    public void onAdLoadError(AdError adError) {
        // Called when a ad fails to load for the given placementId.
    }
});
multiNativeAd.setAdActionListener(new IAdListener.AdActionListener() {
    @Override
    public void onAdImpressionError(AdError adError) {

    }

    @Override
    public void onAdImpression() {

    }

    @Override
    public void onAdClicked() {

    }

    @Override
    public void onAdCompleted() {

    }

    @Override
    public void onAdClosed(boolean b) {

    }
});
multiNativeAd.setLoadAdNum(6);      // request ad number
multiNativeAd.load();
```

### Step 2：Create an XML layout
Create ad item layout.  
The Sample:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:padding="@dimen/san_common_dimens_6dp">

    <com.san.ads.MediaView
        android:id="@+id/ad_image"
        android:layout_width="@dimen/san_common_dimens_80dp"
        android:layout_height="@dimen/san_common_dimens_80dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/ad_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/san_common_dimens_6dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/ad_image"
        tools:text="Magic App" />

</androidx.constraintlayout.widget.ConstraintLayout>

```

### Step 3：Show Native Ads

- Get SANMultiNativeAd to show

```java
SANMultiNativeAd multiNativeAd; // The BaseNativeAd is obtained from void onAdLoaded

// Create SANNativeAdRenderer to render item view
SANNativeAdRenderer adRenderer = new SANNativeAdRenderer(
    new SViewBinder.Builder(R.layout.layout_multi_native_item)
            .iconImageId(R.id.ad_image)     // icon should be MediaView
            .titleId(R.id.ad_text)
            .build();
)

// You can get native ads, this list's size <= load num
List<INativeAd> nativeAds = multiNativeAd.getAdList();

// You can create a view with SANNativeAdRenderer, this code can use in RecyclerView's adpater
View view = adRenderer.createAdView(context, null, parent);

// Use this to render view, for example in onBindViewHolder
adRenderer.renderAdView(view, nativeAds.get(0));
```
- Ad attribution

You must display an ad attribution to denote that the view is an advertisement
