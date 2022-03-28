package com.example.mp3player.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class NotificationActionReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (
            intent?.action == MusicBarNotification.ACTION_NEXT ||
            intent?.action == MusicBarNotification.ACTION_PLAY ||
            intent?.action == MusicBarNotification.ACTION_PREVIOUS
        ){
            val serviceIntent = Intent(context, MusicBarService::class.java)
            serviceIntent.putExtra(MusicBarService.ACTION_KEY, intent.action)
            context?.startService(serviceIntent)
        }
    }

}