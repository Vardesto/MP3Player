package com.example.mp3player.notification

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.mp3player.interfaces.MusicPlayer
import com.example.mp3player.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MusicBarService : Service() {

    @Inject lateinit var musicPlayer: MusicPlayer


    //RUNTIME ERROR
    override fun onCreate() {
        super.onCreate()
    }

    private lateinit var mainViewModel: MainViewModel

    companion object{
        const val ACTION_KEY = "MUSIC_BAR_SERVICE_ACTION_KEY"
    }

    private val musicBarServiceBinder = MusicBarServiceBinder()

    override fun onBind(intent: Intent?): IBinder {
        return musicBarServiceBinder
    }

    inner class MusicBarServiceBinder: Binder(){
        fun getService(viewModel: MainViewModel): MusicBarService{
            mainViewModel = viewModel
            return this@MusicBarService
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.getStringExtra(ACTION_KEY)){
            MusicBarNotification.ACTION_PLAY -> musicPlayer.resumePausePlaying()
            MusicBarNotification.ACTION_NEXT -> musicPlayer.setNext(mainViewModel)
            MusicBarNotification.ACTION_PREVIOUS -> musicPlayer.setPrev(mainViewModel)
        }
        return super.onStartCommand(intent, flags, startId)
    }
}