package com.example.murun.web

import com.example.murun.domain.SongResponseDto
import com.example.murun.domain.SongService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/song")
@RestController
class MusicController(
        private val songService: SongService
) {

    @GetMapping
    @ResponseBody
    fun findSong(@RequestParam bpm: Int): List<SongResponseDto> {
        println("bpm :${bpm}")
        return songService.getCorrectBpmSong(bpm)
    }

    @PostMapping
    fun addSong(@RequestPart file: MultipartFile): SongResponseDto{
        println("file: ${file.originalFilename}")
        return songService.addSong(file)
    }

}