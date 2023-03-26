package com.example.murun.domain

import org.springframework.web.multipart.MultipartFile

data class SongRequestDto(val title: String, val artist: String, val bpm: Int, val albumImage: MultipartFile, val song: MultipartFile)