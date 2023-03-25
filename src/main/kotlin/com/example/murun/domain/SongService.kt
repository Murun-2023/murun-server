package com.example.murun.domain

import com.example.murun.infrastructure.aws.S3UploaderService
import com.example.murun.infrastructure.queryDsl.SongEntity
import com.example.murun.infrastructure.queryDsl.SongRepository
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.audio.mp3.MP3File
import org.jaudiotagger.tag.FieldKey
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Service
class SongService(
        private val songJpaRepository: SongRepository,
        private val s3UploaderService: S3UploaderService
) {

    fun getCorrectBpmSong(bpm: Int): List<SongResponseDto> {
        return songJpaRepository.getCorrectBpmSong(bpm)
                .map {
                    convertDto(it)
                }.toList()
    }

    fun addSong(multipartFile: MultipartFile): SongResponseDto {
        val file = File(multipartFile.originalFilename)
        file.writeBytes(multipartFile.bytes)

        val mp3: MP3File = AudioFileIO.read(file) as MP3File
        file.delete()

        val title: String = mp3.tag.getFirst(FieldKey.TITLE)
        val bpm: String = mp3.tag.getFirst(FieldKey.BPM)
        val uuid: String = UUID.randomUUID().toString()
        val url = s3UploaderService.uploadBpmFile(multipartFile, title, bpm)
        songJpaRepository.saveBpmSong(bpm.toInt(), uuid, url)
        return SongResponseDto(uuid, url)
    }

    private fun geSongTitle(file: MultipartFile) {

    }

    private fun convertDto(song: SongEntity): SongResponseDto {
        return SongResponseDto(song.uuid, song.downloadUrl)
    }
}