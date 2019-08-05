package com.example.kebunrayabanua.main.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class FirebaseMessagingService : FirebaseMessagingService(), AnkoLogger {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        info("From: ${remoteMessage?.from}")

        // Check if message contains a data payload.
        remoteMessage?.data?.isNotEmpty()?.let {
            info("Message data payload: " + remoteMessage.data)

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.

            } else {
                // Handle message within 10 seconds

            }
        }

        // Check if message contains a notification payload.
        remoteMessage?.notification?.let {
            info("Message Notification Body: ${it.body}")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

}