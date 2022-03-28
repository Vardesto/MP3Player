package com.example.mp3player.notification

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
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