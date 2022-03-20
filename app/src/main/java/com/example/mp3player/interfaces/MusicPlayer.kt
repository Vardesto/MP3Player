package com.example.mp3player.interfaces

import android.media.MediaPlayer

interface MusicPlayer {

    fun startPlaying(mediaPlayer: MediaPlayer, path: String)

    fun resumePausePlaying(mediaPlayer: MediaPlayer)

    fun stopPlaying (mediaPlayer: MediaPlayer)

}