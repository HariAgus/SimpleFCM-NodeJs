package com.example.tesnotifwithnodejs

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NofiticationService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("msg", "onMessageReceived : " + remoteMessage.data["message"])
        if (remoteMessage == null) return
        if (remoteMessage.notification != null) {
            notify(remoteMessage.notification!!.title,
                    remoteMessage.notification!!.body)
        }
    }

    fun notify(title: String?, body: String?) {
        val intent = Intent(this, MainActivity::class.java)
        val build = TaskStackBuilder.create(this)
        build.addNextIntentWithParentStack(intent)
        val result = build.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(this, "notification_channel")
                .setSmallIcon(R.mipmap.icon_app)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
        builder.setContentIntent(result)
        val managerCompat = NotificationManagerCompat.from(this)
        managerCompat.notify(100, builder.build())
    }
}