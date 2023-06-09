package com.example.murun.domain

data class SongResponseDto(
        val uuid: String,
        val title: String,
        val artist: String,
        val time: Long,
        val albumImage: String,
        val url: String)