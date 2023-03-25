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

@Service
class SongService(
        private val songJpaRepository: SongRepository,
        private val s3UploaderService: S3UploaderService
) {

    fun getCorrectBpmSong(bpm: Int): List<SongResponseDto>{
        return songJpaRepository.getCorrectBpmSong(bpm)
                .map{
                    convertDto(it)
                }.toList()
    }

    fun addSong(multipartFile: MultipartFile){
        val file: File = File(multipartFile.originalFilename)
        file.writeBytes(multipartFile.bytes)

        val mp3: MP3File = AudioFileIO.read(file) as MP3File
        file.delete()

        val title: String = mp3.tag.getFirst(FieldKey.TITLE)
        println("title:${title}")

    }

    private fun geSongTitle(file: MultipartFile){

    }

    private fun convertDto(songEntity: SongEntity): SongResponseDto{
        return SongResponseDto(songEntity.uuid, songEntity.downloadUrl)
    }
}