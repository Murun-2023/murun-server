package com.example.murun.domain

import com.example.murun.exception.CustomException
import com.example.murun.exception.Error
import com.example.murun.infrastructure.aws.S3UploaderService
import com.example.murun.infrastructure.queryDsl.SongEntity
import com.example.murun.infrastructure.queryDsl.SongRepository
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.audio.mp3.MP3File
import org.jaudiotagger.tag.FieldKey
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.commons.CommonsMultipartFile
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.lang.RuntimeException
import java.util.*
import javax.imageio.ImageIO

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

    fun addSong(songRequestDto: SongRequestDto): SongResponseDto {
        var tmpTitle: String = songRequestDto.title
        var tmpArtist: String = songRequestDto.artist
        var tmpBpm: Int = songRequestDto.bpm
        var tmpTime: String = songRequestDto.time
        var song: MultipartFile = songRequestDto.song
        var albumImage: MultipartFile = songRequestDto.albumImage
        var tmpAlbumImage: File

        val file = File(song.originalFilename)
        var imageFile = File(albumImage.originalFilename)

        var time: Long = getTime(tmpTime)

        file.writeBytes(song.bytes)
        imageFile.writeBytes(albumImage.bytes)

        tmpAlbumImage = imageFile

        val mp3: MP3File = AudioFileIO.read(file) as MP3File
        file.delete()

        try {
            println("mp3 artist: ${mp3.tag.getFirst(FieldKey.ARTIST)}")
            tmpArtist = if (mp3.tag.getFirst(FieldKey.ARTIST) == "") songRequestDto.artist else mp3.tag.getFirst(FieldKey.ARTIST)
        } catch (e: Exception) {
            e.stackTrace
        }

        try {
            tmpTitle = if (mp3.tag.getFirst(FieldKey.TITLE) == "") songRequestDto.title else mp3.tag.getFirst(FieldKey.TITLE)
        } catch (e: Exception) {
            e.stackTrace
        }
        try {
            tmpBpm = songRequestDto.bpm
            //tmpBpm = mp3.tag.getFirst(FieldKey.BPM).toInt()
        } catch (e: Exception) {
            e.stackTrace
        }
        try {
            //val imageData = mp3.tag.firstArtwork.binaryData
            val imageData = songRequestDto.albumImage
            //val image = ImageIO.read(ByteArrayInputStream(imageData))
            imageFile = File("${tmpTitle}.png")
            val fileOutputStream = FileOutputStream(imageFile)
            fileOutputStream.write(imageData.bytes)
            fileOutputStream.close()
            //ImageIO.write(image, "png", imageFile)
            tmpAlbumImage = imageFile
        } catch (e: Exception) {
            e.stackTrace
        }
        val uuid: String = UUID.randomUUID().toString()

        //추출 값
        println("======---title:${tmpTitle}, artist:${tmpArtist} bpm:${tmpBpm}, image: ${tmpAlbumImage}")

        //albumImage 저장
        val albumImageUrl = uploadAlbumImage(tmpAlbumImage, tmpTitle, tmpBpm)
        if (tmpBpm in 60..90) {
            uploadAlbumImage(tmpAlbumImage, tmpTitle, tmpBpm * 2)
        }
        if (tmpBpm in 120..180) {
            uploadAlbumImage(tmpAlbumImage, tmpTitle, tmpBpm / 2)
        }
        //song 파일 저장
        val songUrl = uploadSong(song, tmpTitle, tmpBpm)
        tmpAlbumImage.delete()

        songJpaRepository.saveBpmSong(tmpTitle, tmpArtist, time, albumImageUrl, tmpBpm, uuid, songUrl)
        return SongResponseDto(uuid, tmpTitle, tmpArtist, time, albumImageUrl, songUrl)
    }

    fun getCorrectUUIDSong(uuid: String): SongResponseDto {
        println("uuid 조회")
        val song = songJpaRepository.getCorrectUUIDSong(uuid)
        println("song${song}")
        song?.let {
            return convertDto(song)
        }
        println("예외터짐")
        throw CustomException(Error.NOT_FOUND_SONG)
    }

    private fun getTime(time: String): Long {
        var timeSplit = time.split(":")
        println("first:${timeSplit[0].toLong() * 60}")
        println("second:${timeSplit[1].toLong() }")
        return (timeSplit[0].toLong() * 60 + (timeSplit[1].toLong())) * 1000L
    }

    private fun uploadAlbumImage(albumImage: File, title: String, bpm: Int): String {
        return s3UploaderService.uploadAlbumImage(albumImage, title, bpm)
    }

    private fun uploadSong(song: MultipartFile, title: String, bpm: Int): String {
        return s3UploaderService.uploadSongFile(song, title, bpm)
    }


    private fun convertDto(song: SongEntity): SongResponseDto {
        return SongResponseDto(song.uuid, song.title, song.artist, song.time, song.albumImage, song.downloadUrl)
    }
}