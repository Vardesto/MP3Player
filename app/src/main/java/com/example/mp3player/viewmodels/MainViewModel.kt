package com.example.mp3player.viewmodels

import androidx.lifecycle.ViewModel
import com.example.mp3player.data.audio.AudioModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val musicList: List<AudioModel>) : ViewModel() {

    private val _currentAudioModel = MutableStateFlow(musicList.first())
    val currentAudioModel = _currentAudioModel.asStateFlow()

    fun setNext() {
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

    fun setCurrentAudioModel(audioModel: AudioModel){
        _currentAudioModel.value = audioModel
    }
}