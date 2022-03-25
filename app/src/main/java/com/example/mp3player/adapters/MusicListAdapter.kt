package com.example.mp3player.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mp3player.R
import com.example.mp3player.data.audio.AudioModel
import com.example.mp3player.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MusicListAdapter(
    private var musicList: List<AudioModel>,
    private val navController: NavController,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_music_list, parent, false)
        return MusicListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicListViewHolder, position: Int) {
        holder.bind(musicList[position])
    }

    override fun getItemCount(): Int = musicList.size

    inner class MusicListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(audioModel: AudioModel) {
            val title = itemView.findViewById<TextView>(R.id.title)
            val artist = itemView.findViewById<TextView>(R.id.artist)
            title.text = audioModel.title
            artist.text = audioModel.artist
            if (audioModel == viewModel.currentAudioModel.value){
                title.setTextColor(Color.RED)
            } else {
                title.setTextColor(Color.BLACK)
            }

            itemView.setOnClickListener {
                viewModel.setCurrentAudioModel(audioModel)
                navController.navigate(R.id.action_musicListFragment_to_musicDetailFragment)
            }

        }

    }

}