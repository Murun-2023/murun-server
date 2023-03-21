package com.example.murun.controller

import com.example.murun.service.S3UploaderService
import org.springframework.core.io.ResourceLoader
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class MusicController(private val resourceLoader: ResourceLoader, private val s3UploaderService: S3UploaderService) {

    @GetMapping("/song")
    @ResponseBody
    fun findMusic(@RequestParam bpm: Int): ResponseEntity<Any?> {
        println("bpm :${bpm}")
        val fileResource = resourceLoader.getResource("classpath:/static/test.mp3")
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test.mp3\"")
                .body(fileResource)
    }

}