package com.example.mp3player.data.di.factories

import android.content.Context
import androidx.navigation.NavController
import com.example.mp3player.adapters.MusicListAdapter
import com.example.mp3player.data.audio.AudioModel
import com.example.mp3player.viewmodels.MainViewModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@AssistedFactory
interface MusicListAdapterFactory {
    fun create(
        musicList: List<AudioModel>,
        navController: NavController,
        viewModel: MainViewModel
    ): MusicListAdapter
}