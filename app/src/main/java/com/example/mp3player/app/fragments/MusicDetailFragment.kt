package com.example.mp3player.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mp3player.R
import com.example.mp3player.data.audio.AudioModel
import com.example.mp3player.interfaces.MusicPlayer
import com.example.mp3player.viewmodels.MainViewModel
import com.example.mp3player.viewmodels.MusicDetailFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MusicDetailFragment : Fragment(R.layout.fragment_music_detail) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val musicDetailFragmentViewModel: MusicDetailFragmentViewModel by viewModels()

    private lateinit var audioModel: AudioModel

    private var isTracking = false

    @Inject
    lateinit var musicPlayer: MusicPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //INIT VIEWS
        val title = view.findViewById<TextView>(R.id.musicTitle)
        val artist = view.findViewById<TextView>(R.id.musicArtist)
        val seekBar = view.findViewById<SeekBar>(R.id.seekBar)
        val runTime = view.findViewById<TextView>(R.id.runTime)
        val endTime = view.findViewById<TextView>(R.id.endTime)
        val playPauseButton = view.findViewById<ImageView>(R.id.playPause)
        val nextButton = view.findViewById<ImageView>(R.id.skipNext)
        val prevButton = view.findViewById<ImageView>(R.id.skipPrev)
        //INIT AUDIO_MODEL AND SUBSCRIBE AUDIO_MODEL
        audioModel = mainViewModel.currentAudioModel.value
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.currentAudioModel.collect {
                    audioModel = it
                    //SET VIEWS
                    (activity as AppCompatActivity).supportActionBar?.title = audioModel.title
                    title.text = audioModel.title
                    artist.text = audioModel.artist
                    seekBar.max = audioModel.duration.toInt()
                    endTime.text = convertTime(audioModel.duration.toInt())
                    runTime.text = getString(R.string.start_time)
                    seekBar.max = audioModel.duration.toInt()
                }
            }
        }
        //SUBSCRIBE PLAYER PROGRESS
        lifecycleScope.launch {
            musicDetailFragmentViewModel.currentPositionFlow.collect {
                if (!isTracking) {
                    seekBar.progress = it
                    runTime.text = convertTime(it)
                }
            }
        }
        //SET SEEK_BAR PROGRESS CHANGING
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isTracking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                musicDetailFragmentViewModel.seekTo(seekBar!!.progress)
                isTracking = false
            }
        })
        //SUBSCRIBE IS_PLAYING
        lifecycleScope.launch {
            mainViewModel.isPlaying.collect {
                playPauseButton.setImageResource(if (it) R.drawable.ic_pause else R.drawable.ic_play)
            }
        }
        //SET BUTTONS
        playPauseButton.setOnClickListener {
            musicPlayer.resumePausePlaying(mainViewModel)
        }
        nextButton.setOnClickListener {
            musicPlayer.setNext(mainViewModel)
        }
        prevButton.setOnClickListener {
            musicPlayer.setPrev(mainViewModel)
        }
        //SET MEDIA PLAYER
        if (arguments?.getBoolean(IS_CURRENT_KEY) != true) {
            musicPlayer.startPlaying(mainViewModel)
        }
    }

    private fun convertTime(position: Int): String {
        val time = position.div(1000)
        return String.format("%02d:%02d", time / 60, time % 60)
    }

    companion object {
        const val IS_CURRENT_KEY = "MUSIC_DETAIL_FRAGMENT_IS_CURRENT_KEY"
    }
}