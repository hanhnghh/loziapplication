package com.appforall.loziapplication

/**
 * Created by Hanh Nguyen on 8/13/2017.
 */

data class UpdateInfoEntity (
        /*{
              "banner_ads": "ca-app-pub-1549215738778675/3552869229",
              "interstitial_ads_1": "ca-app-pub-1549215738778675/8039920909",
              "interstitial_ads_2": "ca-app-pub-1549215738778675/1617627564",
              "interstitial_ads_3": "ca-app-pub-1549215738778675/5229569516",
              "update": "false",
              "update_title": "Update de",
              "update_message": "Update nao",
              "update_cancelable": "true",
              "update_link": "https://play.google.com/store/apps/details?id=com.wifihackernetwork.wifihackprank"
            }
        */

        var banner_ads: String?,
        var interstitial_ads_1: String?,
        var interstitial_ads_2: String?,
        var interstitial_ads_3: String?,
        var update: Boolean,
        var update_title: String?,
        var update_message: String?,
        var update_cancelable: Boolean,
        var update_link: String?

)