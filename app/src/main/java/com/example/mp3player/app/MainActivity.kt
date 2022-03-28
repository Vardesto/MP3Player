package com.example.mp3player.app

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mp3player.R
import com.example.mp3player.data.permissions.PermissionsManager
import com.example.mp3player.interfaces.MusicPlayer
import com.example.mp3player.notification.MusicBarService
import com.example.mp3player.notification.MusicBarServiceConnectionImpl
import com.example.mp3player.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    @Inject lateinit var mediaPlayer: MediaPlayer
    @Inject lateinit var musicPlayer: MusicPlayer
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //INIT AND SET NAVIGATION, BOTTOM MENU AND ACTION BAR
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomMenu)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.musicListFragment, R.id.settingsFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        //REQUEST PERMISSIONS
        PermissionsManager.requestPermissions(this)
        //SET OnCompletionListener FOR MEDIA PLAYER
        mediaPlayer.setOnCompletionListener {
            musicPlayer.setNext(mainViewModel)
        }
        //INIT MEDIA PLAYER
        musicPlayer.startPlaying(mainViewModel.currentAudioModel.value.path)
        musicPlayer.resumePausePlaying()
        musicPlayer.setNotification(mainViewModel.currentAudioModel.value)
        //BIND MUSIC_BAR_SERVICE
        bindService(Intent(this, MusicBarService::class.java), MusicBarServiceConnectionImpl(mainViewModel), BIND_AUTO_CREATE)
    }

}