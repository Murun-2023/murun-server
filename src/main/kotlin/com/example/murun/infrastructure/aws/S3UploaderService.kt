package com.example.murun.infrastructure.aws

import org.springframework.web.multipart.MultipartFile

interface S3UploaderService {
    fun getBpmFile(bpm: Int)
    fun uploadBpmFile(file: MultipartFile)
}