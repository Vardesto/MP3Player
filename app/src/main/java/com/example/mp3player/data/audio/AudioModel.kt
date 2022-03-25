package com.example.mp3player.data.audio

import java.io.Serializable

data class AudioModel(
    var title: String,
    var path: String,
    var duration: String,
    var artist: String,
) : Serializable