package com.example.mp3player.data.di

import android.app.Activity
import android.content.Context
import android.media.MediaPlayer
import android.provider.MediaStore
import androidx.lifecycle.ViewModelProvider
import com.example.mp3player.data.audio.AudioModel
import com.example.mp3player.interfaces.MusicPlayer
import com.example.mp3player.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object MainActivityModule {

}