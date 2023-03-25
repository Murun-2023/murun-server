package com.example.murun.web

import com.example.murun.domain.SongResponseDto
import com.example.murun.domain.SongService
import com.example.murun.infrastructure.aws.S3UploaderService
import org.springframework.core.io.ResourceLoader
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MusicController(
        private val s3UploaderService: S3UploaderService,
        private val songService: SongService
) {

    @GetMapping("/song")
    @ResponseBody
    fun findMusic(@RequestParam bpm: Int): List<SongResponseDto> {
        println("bpm :${bpm}")
        return songService.getCorrectBpmSong(bpm)
    }

}