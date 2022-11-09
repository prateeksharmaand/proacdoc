/*************************************************
 * Created by Efendi Hariyadi on 13/10/22, 12:56 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 13/10/22, 12:56 PM
 ************************************************/

package health.care.ai.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import health.care.ai.R
import health.care.ai.ui.home.HomeActivity
import health.care.ai.ui.medication.MedicationActivity


class MedicineService : IntentService("MedicineService") {
    override fun onHandleIntent(intent: Intent?) {
        val extras = intent!!.extras

        val message = extras!!.getString("message")
        val id = extras!!.getInt("id")

        if (message != null) {
            createNotification(
                message, this, id
            )
        }

    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                "CHANNEL_ID",
                "CHANNEL_ID",
                importance
            ).apply {
                description = "Reminder Channel Description"
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification(message: String, context: Context, id: Int) {

        createNotificationChannel(context)

        val intent = Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val mNotificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder = NotificationCompat.Builder(context, "CHANNEL_ID")


        val customNotificationView = RemoteViews(
            packageName,
            R.layout.notification_status_bar
        )


        val takenintent = Intent(context, MedicationActivity::class.java)
        val skipintent = Intent(context, MedicationActivity::class.java)

        takenintent.putExtra("REQUEST_CODE", 151)
        takenintent.putExtra("id", id)


        skipintent.putExtra("REQUEST_CODE", 152)
        skipintent.putExtra("id", id)


        val taken = PendingIntent.getActivity(applicationContext, 151, takenintent,  PendingIntent.FLAG_UPDATE_CURRENT)
        val skip = PendingIntent.getActivity(applicationContext, 152, skipintent,  PendingIntent.FLAG_UPDATE_CURRENT)
        customNotificationView.setOnClickPendingIntent(R.id.btnTaken, taken);
        customNotificationView.setOnClickPendingIntent(R.id.btnSkip, skip);













        customNotificationView.setTextViewText(R.id.txtDesc, message);


        mBuilder.setChannelId("CHANNEL_ID")
        mBuilder.setCustomContentView(customNotificationView)

        mBuilder.setSmallIcon(R.drawable.ic_plus_white_24dp)
        mBuilder.setAutoCancel(true)

        mBuilder.setDefaults(Notification.DEFAULT_ALL)
        mBuilder.setLights(Color.WHITE, 500, 500)
        mBuilder.setContentTitle("Its Time To Take Your Medicine")
        mBuilder.setContentText(message)
        mBuilder.priority = IMPORTANCE_HIGH
        mBuilder.setContentIntent(pendingIntent)
        mNotificationManager.notify(1000, mBuilder.build())


    }


}