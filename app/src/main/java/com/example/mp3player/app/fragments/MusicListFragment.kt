package com.example.mp3player.app.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mp3player.R
import com.example.mp3player.adapters.MusicListAdapter
import com.example.mp3player.data.audio.AudioModel
import com.example.mp3player.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MusicListFragment : Fragment(R.layout.fragment_music_list) {

    @Inject
    lateinit var audioModelList: List<AudioModel>

    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val adapterInstance = MusicListAdapter(audioModelList, findNavController(), viewModel)
        recyclerView.adapter = adapterInstance
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}