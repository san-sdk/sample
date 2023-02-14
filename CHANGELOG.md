# Change Log
> The fourth digit of the SDK version number is recommended to use `+`, Last digit of the version number is the iteration that fixes the issue and `+` is recommended to ensure that the most stable version is used.

## 3.13.1.2
- Add splash Ad
- Reduce SDK size
- Some optimization
- Fix some bugs

## 3.12.7.7
- Improve the success rate of net interface
- Optimize SanAdSdk#notifyConsentStatus 
- Fix unzip-security

## (Deprecated) 3.12.6.2
- Improve the success rate of net interface
- add SanAdSdk#notifyConsentStatus

## 3.11.+(8.1-2022)
- Optimize MediaView, Add VideoOptions api
```Java
VideoOptions videoOptions = new VideoOptions.Builder()
            .setStartMuted(false)
            .setSoundGravity(Gravity.START)
            .build();
mediaView.loadMadsMediaView(nativeAd.getNativeAd(), videoOptions);
```

- Fix some bugs

## 3.6.+(3.15-2021)
- Optimize mediation strategies
- Fix some bugs

## 3.5.+(1.25-2021)
- Optimize mediation adapters
- Update ExoPlayer version to 2.16.0

## 3.4.+(12.20-2021)
- Offline Ad optimisation

## 3.3.+(11.26-2021)
- Update target sdk version to android 30
- Add [Mediation Test Suite](https://github.com/san-sdk/sample/wiki/Test-Suite)
- Update Adapters version
- Fix some bugs

## 3.2.0.+(11.5-2021)
- Memory optimisation
- Fix some bugs

## 3.1.0.+(10.28-2021)
- Support Mediation
  [Mintegral](https://github.com/san-sdk/sample/wiki/Mediation-Mintegral)
  
- Optimize full-screen ad experience
- Fix some bugs

## 2.0.2.+(10.12-2021)
- Improve performance
- Fix some rare Bug

## 2.0.1.+(9.18-2021)
- Add capping and pacing control for mediation
- Update targetSdkVersion to 30

## 1.3.0.4(9.6-2021)
- Support Mediation

  [AdColony](https://github.com/san-sdk/sample/wiki/Mediation-AdColony)</Br>
  [AdMob](https://github.com/san-sdk/sample/wiki/Mediation-AdMob)</Br>
  [AppLovin](https://github.com/san-sdk/sample/wiki/Mediation-AppLovin)</Br>
  [FacebookAudienceNetwork](https://github.com/san-sdk/sample/wiki/Mediation-FacebookAudienceNetwork)</Br>
  [Fyber](https://github.com/san-sdk/sample/wiki/Mediation-Fyber)</Br>
  [IronSource](https://github.com/san-sdk/sample/wiki/Mediation-IronSource)</Br>
  [MoPub](https://github.com/san-sdk/sample/wiki/Mediation-MoPub)</Br>
  [Pangle](https://github.com/san-sdk/sample/wiki/Mediation-Pangle)</Br>
  [PubNative](https://github.com/san-sdk/sample/wiki/Mediation-PubNative)</Br>
  [UnityAds](https://github.com/san-sdk/sample/wiki/Mediation-UnityAds)</Br>
  [Vungle](https://github.com/san-sdk/sample/wiki/Mediation-Vungle)</Br>
  
- Several bug fixes.

## 1.1.0.5(8.5-2021)
- Support Native Ads
- Support Admob Custom Event
- Support MoPub Custom

## 1.0.0.0(7.1-2021)
Initial version
- Support Banner Ads
- Support Interstitial Ads
- Support Rewarded Ads
