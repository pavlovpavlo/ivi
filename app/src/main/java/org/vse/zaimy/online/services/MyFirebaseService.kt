package org.vse.zaimy.online.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.vse.zaimy.online.R
import org.vse.zaimy.online.ui.splash.SplashScreenActivity
import java.net.HttpURLConnection
import java.net.URL


class MyFirebaseService : FirebaseMessagingService() {
    var bitmap: Bitmap? = null


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)


        val title = remoteMessage.notification?.title
        val message = remoteMessage.notification?.body
        val type = remoteMessage.data["link"]
        val imageUri: Uri? = remoteMessage.notification?.imageUrl
        bitmap = if (imageUri != null && imageUri.toString() != "") {
            getBitmapFromUrl(imageUri.toString())
        } else {
            null
        }
        sendNotification(title.toString(), message.toString(), bitmap, type.toString())
    }

    private fun sendNotification(
        messageTitle: String,
        messageBody: String,
        image: Bitmap?,
        type: String?
    ) {
        val configureIntent = Intent(applicationContext, SplashScreenActivity::class.java)

        if (type != null && type != "") {
            val screenType = if (type.indexOf("/") != -1)
                type.substring(0, type.indexOf("/"))
            else
                type
            configureIntent.putExtra("type_push", screenType)
            if (type.indexOf("/") != -1) {
                val id = type.substring(type.indexOf("/") + 1)
                configureIntent.putExtra("id", id)
            }
        }
        configureIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingClearScreenIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            configureIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ivi_smal)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setContentIntent(pendingClearScreenIntent)
        if (image != null) {
            notificationBuilder.setLargeIcon(image).setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(image)
            )
        }
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "org.vse.zaimy.online.chanel"
            val channel = NotificationChannel(
                channelId,
                "IVI",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.setShowBadge(true)
            notificationManager.createNotificationChannel(channel)
            notificationBuilder.setChannelId(channelId)
        }
        notificationManager.notify(101, notificationBuilder.build())
    }

    private fun getBitmapFromUrl(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            null
        }
    }

    companion object {
        private const val TAG = "FirebaseMessageService"
    }
}