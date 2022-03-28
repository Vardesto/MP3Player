package com.example.mp3player.viewmodels

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MusicDetailFragmentViewModel @Inject constructor(private val mediaPlayer: MediaPlayer): ViewModel() {

    val currentPositionFlow = flow{
        while (true){
            emit(mediaPlayer.currentPosition)
            delay(500L)
        }
    }

    fun seekTo(progress: Int){
        mediaPlayer.seekTo(progress)
    }
}