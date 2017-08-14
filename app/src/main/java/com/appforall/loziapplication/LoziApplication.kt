package com.appforall.loziapplication

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.preference.PreferenceManager
import android.util.Log
import com.onesignal.OSNotification
import com.onesignal.OSNotificationAction
import com.onesignal.OSNotificationOpenResult
import com.onesignal.OneSignal

/**
 * Created by hanhanh.nguyen on 8/14/2017.
 */
class LoziApplication : Application(){

    override fun onCreate(){
        super.onCreate()
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(OneSignalNotificationOpenedHandler())
                .setNotificationReceivedHandler(OneSignalNotificationReceivedHandler())
                .init()
    }

    internal inner class OneSignalNotificationOpenedHandler : OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.
        override fun notificationOpened(result: OSNotificationOpenResult) {
            val actionType = result.action.type
            val data = result.notification.payload.additionalData
            val customKey: String?

            if (data != null) {
                customKey = data.optString("customkey", null)
                if (customKey != null)
                    Log.i("OneSignalExample", "customkey set with value: " + customKey)
            }

            if (actionType == OSNotificationAction.ActionType.ActionTaken)
                Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID)

            val url = PreferenceManager.getDefaultSharedPreferences(this@LoziApplication).getString(getString(R.string.update_link_key), "")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    internal inner class OneSignalNotificationReceivedHandler : OneSignal.NotificationReceivedHandler {
        override fun notificationReceived(notification: OSNotification) {
            val data = notification.payload.additionalData
            val customKey: String?

            if (data != null) {
                customKey = data.optString("customkey", null)
                if (customKey != null)
                    Log.i("OneSignalExample", "customkey set with value: " + customKey)
            }
        }
    }
}