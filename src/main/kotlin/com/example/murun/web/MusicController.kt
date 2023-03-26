package com.example.murun.web

import com.example.murun.domain.SongRequestDto
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
    fun addSong(@ModelAttribute songRequestDto: SongRequestDto): SongResponseDto {
        println("title:${songRequestDto.title}")
        println("title:${songRequestDto.song}")
        println("bpm:${songRequestDto.bpm}")
        println("title:${songRequestDto.albumImage}")
        println("title:${songRequestDto.artist}")
        val title = songRequestDto.title
        val song = songRequestDto.song
        val bpm = songRequestDto.bpm
        val albumImage = songRequestDto.albumImage
        val artist = songRequestDto.artist
        return songService.addSong(title, artist, bpm, song, albumImage)
    }

}