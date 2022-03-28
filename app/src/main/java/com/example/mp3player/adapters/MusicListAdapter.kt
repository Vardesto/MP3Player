package com.example.mp3player.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mp3player.R
import com.example.mp3player.app.fragments.MusicDetailFragment
import com.example.mp3player.data.audio.AudioModel
import com.example.mp3player.interfaces.MusicPlayer
import com.example.mp3player.viewmodels.MainViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class MusicListAdapter @AssistedInject constructor(
    @Assisted private var musicList: List<AudioModel>,
    @Assisted private val navController: NavController,
    @Assisted private val viewModel: MainViewModel,
    private val musicPlayer: MusicPlayer
) : RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder>() {

    fun notifyCurrentItemChanged(audioModel: AudioModel){
        notifyItemChanged(musicList.indexOf(audioModel))
        notifyItemChanged(musicList.indexOf(viewModel.currentAudioModel.value))
    }

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
            if (audioModel == viewModel.currentAudioModel.value) {
                title.setTextColor(Color.RED)
            } else {
                title.setTextColor(Color.BLACK)
            }

            itemView.setOnClickListener {

                navController.navigate(
                    R.id.action_musicListFragment_to_musicDetailFragment,
                    bundleOf(
                        MusicDetailFragment.IS_CURRENT_KEY to
                                viewModel.setCurrentAudioModel(audioModel)
                    )
                )
                musicPlayer.setNotification(audioModel)

            }

        }

    }

}