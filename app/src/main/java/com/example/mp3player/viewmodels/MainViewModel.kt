package com.example.mp3player.viewmodels

import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mp3player.data.audio.AudioModel
import com.example.mp3player.interfaces.MusicPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val musicList: List<AudioModel>,
    musicPlayer: MusicPlayer,
    mediaPlayer: MediaPlayer
) : ViewModel() {

    private val _currentAudioModel = MutableStateFlow(musicList.first())
    val currentAudioModel = _currentAudioModel.asStateFlow()

    init {
        //SET OnCompletionListener FOR MEDIA PLAYER
        mediaPlayer.setOnCompletionListener {
            musicPlayer.setNext(this)
        }
        //INIT MEDIA PLAYER
        mediaPlayer.setDataSource(currentAudioModel.value.path)
        mediaPlayer.prepare()
    }

    fun getNext(): AudioModel{
        return if (musicList.indexOf(currentAudioModel.value) == musicList.lastIndex) {
            musicList.first()
        } else {
            musicList[musicList.indexOf(currentAudioModel.value) + 1]
        }
    }

    fun getPrev(): AudioModel{
        return if (musicList.indexOf(currentAudioModel.value) == 0) {
            musicList.last()
        } else {
            musicList[musicList.indexOf(currentAudioModel.value) - 1]
        }
    }

    fun setNext(){
        _currentAudioModel.value =
            if (musicList.indexOf(currentAudioModel.value) == musicList.lastIndex) {
                musicList.first()
            } else {
                musicList[musicList.indexOf(currentAudioModel.value) + 1]
            }
    }

    fun setPrev() {
        _currentAudioModel.value =
            if (musicList.indexOf(currentAudioModel.value) == 0) {
                musicList.last()
            } else {
                musicList[musicList.indexOf(currentAudioModel.value) - 1]
            }
    }

    fun setCurrentAudioModel(audioModel: AudioModel): Boolean {
        val result = _currentAudioModel.value == audioModel
        _currentAudioModel.value = audioModel
        return result
    }
}