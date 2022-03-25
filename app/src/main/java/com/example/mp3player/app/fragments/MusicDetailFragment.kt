package com.example.mp3player.app.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mp3player.R
import com.example.mp3player.data.audio.AudioModel
import com.example.mp3player.interfaces.MusicPlayer
import com.example.mp3player.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MusicDetailFragment: Fragment(R.layout.fragment_music_detail) {

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var audioModel: AudioModel

    @Inject lateinit var musicPlayer: MusicPlayer

    private lateinit var title: TextView
    private lateinit var artist: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var runTime: TextView
    private lateinit var endTime: TextView
    private lateinit var playPauseButton: ImageView
    private lateinit var nextButton: ImageView
    private lateinit var prevButton: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //INIT VIEWS
        title = view.findViewById(R.id.musicTitle)
        artist = view.findViewById(R.id.musicArtist)
        seekBar = view.findViewById(R.id.seekBar)
        runTime = view.findViewById(R.id.runTime)
        endTime = view.findViewById(R.id.endTime)
        playPauseButton = view.findViewById(R.id.playPause)
        nextButton = view.findViewById(R.id.skipNext)
        prevButton = view.findViewById(R.id.skipPrev)
        //INIT AND SUBSCRIBE AUDIO_MODEL
        audioModel = mainViewModel.currentAudioModel.value
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED){
                mainViewModel.currentAudioModel.collectLatest {
                    audioModel = it
                    setUI()
                }
            }
        }
        //SET VIEWS
        setUI()
        //SET BUTTONS
        playPauseButton.setOnClickListener { musicPlayer.resumePausePlaying() }
        nextButton.setOnClickListener { musicPlayer.setNext(mainViewModel) }
        prevButton.setOnClickListener { musicPlayer.setPrev(mainViewModel) }
        //SET MEDIA PLAYER



        //musicPlayerImpl.startPlaying(audioModel.path)
    }

    private fun convertTime(string: String): String{
        val timeLong = string.toLong().div(1000)
        return String.format("%02d:%02d", timeLong / 60 ,timeLong % 60)
    }

    private fun setUI(){
        (activity as AppCompatActivity).supportActionBar?.title = audioModel.title
        title.text = audioModel.title
        artist.text = audioModel.artist
        seekBar.max = audioModel.duration.toInt()
        endTime.text = convertTime(audioModel.duration)
        runTime.text = getString(R.string.start_time)
    }
}