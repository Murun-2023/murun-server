package com.example.murun.infrastructure.aws

import org.springframework.web.multipart.MultipartFile
import java.io.File

interface S3UploaderService {
    fun getBpmFile(bpm: Int)
    fun uploadAlbumImage(file: File, title: String, bpm: Int): String
    fun uploadSongFile(file: MultipartFile, title: String, bpm: Int): String
}