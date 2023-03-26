package com.example.murun.web

import com.example.murun.domain.SongRequestDto
import com.example.murun.domain.SongResponseDto
import com.example.murun.domain.SongService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/song")
@RestController
class MusicController(
        private val songService: SongService
) {

    @ApiOperation(value= "bpm에 맞는 곡 반환", notes = "bpm을 인풋으로 받아서, 해당 bpm과 동일한 곡들을 반환합니다.")
    @GetMapping
    @ResponseBody
    fun findSong(@RequestParam bpm: Int): List<SongResponseDto> {
        println("bpm :${bpm}")
        return songService.getCorrectBpmSong(bpm)
    }

    @ApiOperation(value = "곡을 추가합니다.", notes = "곡에 필요한 값을 받아 서버와 데이터베이스에 저장하고, 이를 저장한 객체를 반환합니다.")
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