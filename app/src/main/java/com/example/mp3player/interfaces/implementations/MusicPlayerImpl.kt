package com.example.mp3player.interfaces.implementations

import android.media.MediaPlayer
import com.example.mp3player.interfaces.MusicPlayer
import com.example.mp3player.viewmodels.MainViewModel
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
        mainViewModel.setNext()
        stopPlaying()
        startPlaying(mainViewModel.currentAudioModel.value.path)
    }

    override fun setPrev(mainViewModel: MainViewModel) {
        mainViewModel.setPrev()
        stopPlaying()
        startPlaying(mainViewModel.currentAudioModel.value.path)
    }


}