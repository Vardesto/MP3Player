package com.example.mp3player.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mp3player.R
import com.example.mp3player.adapters.MusicListAdapter
import com.example.mp3player.data.audio.AudioModel
import com.example.mp3player.interfaces.MusicPlayer
import com.example.mp3player.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MusicListFragment : Fragment(R.layout.fragment_music_list) {

    @Inject lateinit var audioModelList: List<AudioModel>

    @Inject lateinit var musicPlayer: MusicPlayer

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var audioModel: AudioModel

    private lateinit var musicTitle: TextView
    private lateinit var playPauseButton: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //INIT NAV CONTROLLER
        val navController = findNavController()
        //SET RECYCLER VIEW AND ADAPTER
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val adapterInstance = MusicListAdapter(audioModelList, navController, mainViewModel)
        recyclerView.adapter = adapterInstance
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //INIT BOTTOM PLAYER
        playPauseButton = view.findViewById(R.id.playPause)
        val nextButton = view.findViewById<ImageView>(R.id.skipNext)
        val prevButton = view.findViewById<ImageView>(R.id.skipPrev)
        musicTitle = view.findViewById(R.id.musicTitle)
        //SET AUDIO_MODEL
        audioModel = mainViewModel.currentAudioModel.value
        //SUBSCRIBE AUDIO_MODEL
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.currentAudioModel.collectLatest {
                    adapterInstance.notifyCurrentItemChanged(audioModel)
                    audioModel = it
                    setBottomPlayerUI()
                }
            }
        }
        //SET BOTTOM_PLAYER
        setBottomPlayerUI()
        //SET LISTENERS FOR BOTTOM_PLAYER
        nextButton.setOnClickListener {
            musicPlayer.setNext(mainViewModel)
        }
        prevButton.setOnClickListener {
            musicPlayer.setPrev(mainViewModel)
        }
        playPauseButton.setOnClickListener {
            playPauseButton.setImageResource(
                if (musicPlayer.isPlaying())
                    R.drawable.ic_play
                else
                    R.drawable.ic_pause
            )
            musicPlayer.resumePausePlaying()
        }
        musicTitle.setOnClickListener{
            navController.navigate(
                R.id.action_musicListFragment_to_musicDetailFragment,
                bundleOf(
                    MusicDetailFragment.IS_CURRENT_KEY to true
                )
            )
        }
    }

    private fun setBottomPlayerUI(){
        musicTitle.text = audioModel.title
        playPauseButton.setImageResource(
            if (musicPlayer.isPlaying())
                R.drawable.ic_pause
            else
                R.drawable.ic_play
        )
    }

}