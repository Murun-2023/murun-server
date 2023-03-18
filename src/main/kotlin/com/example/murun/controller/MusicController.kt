package com.example.murun.controller

import com.example.murun.dataclass.BpmRequestDto
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MusicController (private val resourceLoader: ResourceLoader){

    @GetMapping("/test")
    @ResponseBody
    fun findMusic(@RequestBody requestDto: BpmRequestDto): ResponseEntity<Resource>{
        println("test :${requestDto.bpm}")
        val fileResource = resourceLoader.getResource("classpath:/static/test.mp3")
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test.mp3\"")
                .body(fileResource)
    }

}