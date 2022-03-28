package com.example.mp3player.notification

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import com.example.mp3player.interfaces.MusicPlayer
import com.example.mp3player.viewmodels.MainViewModel

class MusicBarServiceConnectionImpl(private val mainViewModel: MainViewModel): ServiceConnection {

    private var musicBarService: MusicBarService? = null

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicBarService.MusicBarServiceBinder
        musicBarService = binder.getService(mainViewModel)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicBarService = null
    }
}