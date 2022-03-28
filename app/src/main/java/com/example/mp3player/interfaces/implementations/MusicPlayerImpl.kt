package com.example.mp3player.interfaces.implementations

import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.example.mp3player.data.audio.AudioModel
import com.example.mp3player.interfaces.MusicPlayer
import com.example.mp3player.notification.MusicBarNotification
import com.example.mp3player.viewmodels.MainViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MusicPlayerImpl @Inject constructor(
    private val mediaPlayer: MediaPlayer,
    @ApplicationContext private val context: Context): MusicPlayer {

    override fun startPlaying(path: String) {
        mediaPlayer.setDataSource(path)
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    override fun resumePausePlaying() {
        if (mediaPlayer.isPlaying){
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
    }

    override fun stopPlaying() {
        mediaPlayer.stop()
        mediaPlayer.reset()
    }

    override fun setNext(mainViewModel: MainViewModel) {
        stopPlaying()
        startPlaying(mainViewModel.getNext().path)
        mainViewModel.setNext()
        setNotification(mainViewModel.currentAudioModel.value)
    }



    override fun setPrev(mainViewModel: MainViewModel) {
        stopPlaying()
        startPlaying(mainViewModel.getPrev().path)
        mainViewModel.setPrev()
        setNotification(mainViewModel.currentAudioModel.value)

    }

    override fun isCurrent(): Boolean {
        return mediaPlayer.isPlaying
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    override fun setNotification(audioModel: AudioModel) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val notification = MusicBarNotification.createNotification(context, audioModel)
        notificationManager.notify(0, notification)
    }

}