package com.example.murun.controller

import com.example.murun.dataclass.BpmResponseDto
import com.example.murun.service.S3UploaderService
import org.springframework.core.io.ResourceLoader
import org.springframework.web.bind.annotation.*

@RestController
class MusicController(private val resourceLoader: ResourceLoader, private val s3UploaderService: S3UploaderService) {

    @GetMapping("/song")
    @ResponseBody
    fun findMusic(@RequestParam bpm: Int):BpmResponseDto {
        println("bpm :${bpm}")
        val fileResource = resourceLoader.getResource("classpath:/static/test.mp3")
        return BpmResponseDto(1,"https://murun-bucket.s3.amazonaws.com/100bpm/Tobu+-+Back+To+You.mp3")
    }

}