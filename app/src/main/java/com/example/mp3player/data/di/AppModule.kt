package com.example.mp3player.data.di

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.provider.MediaStore
import com.example.mp3player.data.audio.AudioModel
import com.example.mp3player.interfaces.MusicPlayer
import com.example.mp3player.interfaces.implementations.MusicPlayerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMediaPlayer(): MediaPlayer{
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
        )
        return mediaPlayer
    }

    @Singleton
    @Provides
    fun provideMusic(@ApplicationContext context: Context): List<AudioModel>{
        val selection = MediaStore.Audio.Media.IS_MUSIC + " == 1"
        val projection = arrayOf(
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST
        )
        val resultList = mutableListOf<AudioModel>()
        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            null,
            null
        )
        if (cursor != null) {
            while (cursor.moveToNext()) {
                resultList.add(
                    AudioModel(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                    )
                )
            }
        }
        cursor?.close()
        return resultList
    }


    @Module
    @InstallIn(SingletonComponent::class)
    abstract class MusicPlayerModule{
        @Binds
        abstract fun bindMusicPlayer(musicPlayerImpl: MusicPlayerImpl): MusicPlayer
    }

}