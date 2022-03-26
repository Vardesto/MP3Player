package com.example.mp3player.interfaces

import android.media.MediaPlayer
import com.example.mp3player.viewmodels.MainViewModel
import kotlinx.coroutines.flow.Flow

interface MusicPlayer {

    fun startPlaying(path: String)

    fun resumePausePlaying()

    fun stopPlaying ()

    fun setNext(mainViewModel: MainViewModel)

    fun setPrev(mainViewModel: MainViewModel)

    fun isCurrent(): Boolean

    fun isPlaying(): Boolean

}