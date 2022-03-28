package com.example.mp3player.interfaces.implementations

import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.example.mp3player.R
import com.example.mp3player.data.audio.AudioModel
import com.example.mp3player.interfaces.MusicPlayer
import com.example.mp3player.notification.MusicBarNotification
import com.example.mp3player.viewmodels.MainViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicPlayerImpl @Inject constructor(
    private val mediaPlayer: MediaPlayer,
    @ApplicationContext private val context: Context
) : MusicPlayer {

    override fun startPlaying(mainViewModel: MainViewModel) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(mainViewModel.currentAudioModel.value.path)
        mediaPlayer.prepare()
        mediaPlayer.start()
        mainViewModel.changeIsPlaying(true)
        setNotification(mainViewModel.currentAudioModel.value)
    }

    override fun resumePausePlaying(mainViewModel: MainViewModel) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
        mainViewModel.changeIsPlaying()
        setNotification(mainViewModel.currentAudioModel.value)
    }

    override fun stopPlaying() {
        mediaPlayer.stop()
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.cancel(0)
    }

    override fun setNext(mainViewModel: MainViewModel) {
        mainViewModel.setNext()
        startPlaying(mainViewModel)
    }


    override fun setPrev(mainViewModel: MainViewModel) {
        mainViewModel.setPrev()
        startPlaying(mainViewModel)
    }

    override fun setNotification(audioModel: AudioModel) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val iconId = if (mediaPlayer.isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        val notification = MusicBarNotification.createNotification(context, audioModel, iconId)
        notificationManager.notify(0, notification)
    }

}