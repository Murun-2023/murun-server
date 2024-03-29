package com.example.murun.web

import com.example.murun.domain.SongRequestDto
import com.example.murun.domain.SongResponseDto
import com.example.murun.domain.SongService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RequestMapping("/song")
@RestController
class MusicController(
        private val songService: SongService
) {

    @ApiOperation(value = "bpm에 맞는 곡 반환", notes = "bpm을 인풋으로 받아서, 해당 bpm과 동일한 곡들을 반환합니다.")
    @GetMapping
    @ResponseBody
    fun findSong(@RequestParam(defaultValue = "0") bpm: Int,
                 @RequestParam(defaultValue = "") uuid: String): Any {
        println("bpm :${bpm}")
        println("uuid :${uuid}")
        if (bpm >= 0) return songService.getCorrectBpmSong(bpm)
        if (uuid != "") return songService.getCorrectUUIDSong(uuid)
        return Arrays.asList("none")
    }

    @ApiOperation(value = "bpm에 맞는 랜덤 곡 1개를 리턴")
    @GetMapping("/random")
    @ResponseBody
    fun findRandomSong(@RequestParam(defaultValue = "0") bpm: Int): SongResponseDto{
        println("bpm: ${bpm}")
        return songService.getRandomBpmSong(bpm)
    }

    @ApiOperation(value = "곡을 추가합니다.", notes = "곡에 필요한 값을 받아 서버와 데이터베이스에 저장하고, 이를 저장한 객체를 반환합니다.")
    @PostMapping
    fun addSong(@ModelAttribute songRequestDto: SongRequestDto): SongResponseDto {
        println("title:${songRequestDto.title}")
        println("song:${songRequestDto.song}")
        println("bpm:${songRequestDto.bpm}")
        println("time:${songRequestDto.time}")
        println("albumImage:${songRequestDto.albumImage}")
        println("artist:${songRequestDto.artist}")
        return songService.addSong(songRequestDto)
    }

    /*
    @ApiOperation(value = "uuid에 맞는 곡 반환", notes = "uuid를 인풋으로 받아서, 해당 uuidd에 맞는 곡을 반환합니다.")
    @GetMapping("/uuid")
    @ResponseBody
    fun findUUIDSong(@RequestParam uuid: String): SongResponseDto {
        println("uuid: ${uuid}")
        return songService.getCorrectUUIDSong(uuid)
    }
     */
}