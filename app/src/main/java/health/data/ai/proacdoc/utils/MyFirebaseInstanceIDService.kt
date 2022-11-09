/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 3:33 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 21/07/22, 1:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 05/07/22, 3:33 PM
 ************************************************/

/*************************************************
 * Created by Efendi Hariyadi on 18/06/22, 11:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 18/06/22, 11:42 PM
 ************************************************/

package health.data.ai.proacdoc.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import health.care.ai.proacdoc.R

import health.data.ai.proacdoc.ui.login.LoginActivity


class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {


        val data = remoteMessage.data

        val title = data["title"]
        val desc = data["desc"]


        sendNotification(title.toString(),desc.toString())

    }

    override fun onNewToken(token: String) {


    }


    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }



    private fun sendNotification(title:String,des:String) {
        val intent = Intent(this, LoginActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )


        val channelId = "default_notification_channel_id"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)


        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_baseline_trending_24)
            .setContentTitle(title)

            .setContentText(des)

            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
   /* private fun sendNotificationwithimage(title:String,des:String, imgUrl:String) {
        val intent = Intent(this, LoginActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 *//* Request code *//*, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val futureTarget = Glide.with(this)
            .asBitmap()
            .load(imgUrl)
            .submit()
        val bitmap = futureTarget.get()


        Glide.with(this).clear(futureTarget)
        val channelId = "default_notification_channel_id"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .setBigContentTitle(title)
            .setSummaryText(des)
            .bigLargeIcon(bitmap)
            .bigPicture(bitmap)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_baseline_trending_24)
            .setContentTitle(title)
            .setLargeIcon(bitmap)
            .setContentText(des)
            .setStyle(bigPictureStyle)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build())
    }*/
}