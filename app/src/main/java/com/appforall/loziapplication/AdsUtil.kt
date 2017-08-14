package com.appforall.loziapplication

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import rx.Observable

/**
 * Created by hanhanh.nguyen on 8/14/2017.
 */
class AdsUtil constructor(var context : Context){
    var prefs : SharedPreferences
    var ads_string_1 : String
    var ads_string_2 : String
    var ads_string_3 : String
    var mInterstitial_1 : InterstitialAd
    var mInterstitial_2 : InterstitialAd
    var mInterstitial_3 : InterstitialAd

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        mInterstitial_1 = InterstitialAd(context)
        mInterstitial_2 = InterstitialAd(context)
        mInterstitial_3 = InterstitialAd(context)

        ads_string_1 = prefs.getString(context.getString(R.string.interstitial_ads_1), "")
        ads_string_2 = prefs.getString(context.getString(R.string.interstitial_ads_2), "")
        ads_string_3 = prefs.getString(context.getString(R.string.interstitial_ads_3), "")

        mInterstitial_1.adUnitId = ads_string_1
        mInterstitial_2.adUnitId = ads_string_2
        mInterstitial_3.adUnitId = ads_string_3
    }

    fun loadAds() : Observable<InterstitialAd> {
        var adReq = AdRequest.Builder()
                .addTestDevice("Your Device Hash")
                .build()
        mInterstitial_1.loadAd(adReq)

        return Observable.just(mInterstitial_1)
    }

    fun loadAdsNotShow() : InterstitialAd{

        var mInterstitial = when{
            mInterstitial_1.isLoaded -> mInterstitial_1
            mInterstitial_2.isLoaded -> mInterstitial_2
            mInterstitial_3.isLoaded -> mInterstitial_3
            else -> mInterstitial_1
        }

        var adReq1 = AdRequest.Builder()
                .addTestDevice("Your Device Hash")
                .build()
        if(!mInterstitial_1.isLoaded) {
            mInterstitial_1.loadAd(adReq1)
        }

        var adReq2 = AdRequest.Builder()
                .addTestDevice("Your Device Hash")
                .build()
        if(!mInterstitial_2.isLoaded) {
            mInterstitial_2.loadAd(adReq2)
        }

        var adReq3 = AdRequest.Builder()
                .addTestDevice("Your Device Hash")
                .build()
        if(!mInterstitial_3.isLoaded) {
            mInterstitial_3.loadAd(adReq3)
        }

        return mInterstitial
    }
}