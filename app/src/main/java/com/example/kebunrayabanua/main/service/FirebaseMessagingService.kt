package com.example.kebunrayabanua.main.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.example.kebunrayabanua.R
import com.example.kebunrayabanua.main.main.detailEvent.DetailEventActivity
import com.example.kebunrayabanua.main.service.FirebaseMessagingService.FirebaseNotification.CHANNEL_ID
import com.example.kebunrayabanua.main.service.FirebaseMessagingService.FirebaseNotification.CHANNEL_NAME
import com.example.kebunrayabanua.main.service.FirebaseMessagingService.FirebaseNotification.NOTIFICATION_ID
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.jetbrains.anko.AnkoLogger


class FirebaseMessagingService : FirebaseMessagingService(), AnkoLogger {

    object FirebaseNotification {
        const val NOTIFICATION_ID = 1
        var CHANNEL_ID = "channel_01"
        var CHANNEL_NAME: CharSequence = "event"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        val resultIntent = Intent(this, DetailEventActivity::class.java)
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val bigPictureStyle = NotificationCompat.BigPictureStyle()
        bigPictureStyle.bigPicture(BitmapFactory.decodeResource(resources, R.drawable.event_stock_image)).build()

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(remoteMessage?.notification?.title)
                .setContentIntent(resultPendingIntent)
                .setStyle(bigPictureStyle)
                .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT)
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()
        mNotificationManager.notify(NOTIFICATION_ID, notification)

    }

}