package com.example.mp3player.viewmodels

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
    private val musicPlayer: MusicPlayer
) : ViewModel() {

    private val _currentAudioModel = MutableStateFlow(musicList.first())
    val currentAudioModel = _currentAudioModel.asStateFlow()

    private val _isPlaying = MutableStateFlow(true)
    val isPlaying = _isPlaying.asStateFlow()

    fun changeIsPlaying(started: Boolean = false){
        if (started){
            _isPlaying.value = true
        } else {
            _isPlaying.value = !_isPlaying.value
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

    override fun onCleared() {
        super.onCleared()
        musicPlayer.stopPlaying()
    }

    fun setCurrentAudioModel(audioModel: AudioModel): Boolean {
        val result = _currentAudioModel.value == audioModel
        _currentAudioModel.value = audioModel
        return result
    }
}