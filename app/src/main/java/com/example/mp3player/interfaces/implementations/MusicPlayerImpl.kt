package com.example.mp3player.interfaces.implementations

import android.media.MediaPlayer
import android.util.Log
import com.example.mp3player.interfaces.MusicPlayer
import com.example.mp3player.viewmodels.MainViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MusicPlayerImpl @Inject constructor(private val mediaPlayer: MediaPlayer): MusicPlayer {

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

    }

    override fun setPrev(mainViewModel: MainViewModel) {
        stopPlaying()
        startPlaying(mainViewModel.getPrev().path)
        mainViewModel.setPrev()

    }

    override fun isCurrent(): Boolean {
        return mediaPlayer.isPlaying
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

}