package com.example.mp3player.notification

import android.app.Notification
import android.content.Context
import com.example.mp3player.data.audio.AudioModel
import android.app.PendingIntent
import android.content.Intent
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.core.graphics.drawable.toBitmap
import com.example.mp3player.R
class MusicBarNotification {

    companion object{

        const val CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
        const val ACTION_PREVIOUS = "NOTIFICATION_ACTION_PREVIOUS"
        const val ACTION_PLAY = "NOTIFICATION_ACTION_PLAY"
        const val ACTION_NEXT = "NOTIFICATION_ACTION_NEXT"

        fun createNotification(context: Context, audioModel: AudioModel): Notification{

            val mediaSessionCompat = MediaSessionCompat(context, "mediaSessionCompat_notification")

            val intentPrev = Intent(context, NotificationActionReceiver::class.java).setAction(ACTION_PREVIOUS)
            val pendingIntentPrev = PendingIntent.getBroadcast(context, 0, intentPrev, PendingIntent.FLAG_UPDATE_CURRENT)

            val intentPlay = Intent(context, NotificationActionReceiver::class.java).setAction(ACTION_PLAY)
            val pendingIntentPlay = PendingIntent.getBroadcast(context, 0, intentPlay, PendingIntent.FLAG_UPDATE_CURRENT)

            val intentNext = Intent(context, NotificationActionReceiver::class.java).setAction(ACTION_NEXT)
            val pendingIntentNext = PendingIntent.getBroadcast(context, 0, intentNext, PendingIntent.FLAG_UPDATE_CURRENT)

            //val contentIntent = Intent(context, MainActivity::class.java)
            //val pendingIntentContent = PendingIntent.getActivity(context, 0, contentIntent, 0)

            return NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(audioModel.title)
                .setSmallIcon(R.drawable.ic_headset)
                .setLargeIcon(getDrawable(context.resources, R.drawable.music_icon, null)?.toBitmap())
                .addAction(R.drawable.ic_previous, "Previous", pendingIntentPrev)
                .addAction(R.drawable.ic_pause, "Play", pendingIntentPlay)
                .addAction(R.drawable.ic_next, "Next", pendingIntentNext)
                //.setContentIntent(pendingIntentContent)
                .setOnlyAlertOnce(true)
                .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSessionCompat.sessionToken)
                    .setShowActionsInCompactView(0, 1, 2)
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
        }

    }
}