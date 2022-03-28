package com.example.mp3player.interfaces

import com.example.mp3player.data.audio.AudioModel
import com.example.mp3player.viewmodels.MainViewModel

interface MusicPlayer {

    fun startPlaying(mainViewModel: MainViewModel)

    fun resumePausePlaying(mainViewModel: MainViewModel)

    fun stopPlaying ()

    fun setNext(mainViewModel: MainViewModel)

    fun setPrev(mainViewModel: MainViewModel)

    fun setNotification(audioModel: AudioModel)

}