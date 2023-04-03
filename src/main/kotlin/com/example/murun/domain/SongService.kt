package com.example.murun.domain

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

    fun addSong(title: String, artist: String, bpm: Int, albumImage: MultipartFile, song: MultipartFile): SongResponseDto {
        var tmpTitle: String = title
        var tmpArtist: String = artist
        var tmpBpm: Int  = bpm
        var tmpAlbumImage: File

        val file = File(song.originalFilename)
        var imageFile = File(albumImage.originalFilename)

        file.writeBytes(song.bytes)
        imageFile.writeBytes(albumImage.bytes)

        tmpAlbumImage = imageFile

        val mp3: MP3File = AudioFileIO.read(file) as MP3File
        file.delete()

        try{
            println("mp3 artist: ${mp3.tag.getFirst(FieldKey.ARTIST)}")
            tmpArtist = if(mp3.tag.getFirst(FieldKey.ARTIST) == "") artist else mp3.tag.getFirst(FieldKey.ARTIST)
        } catch (e: Exception){
            e.stackTrace
        }

        try{
            tmpTitle= if(mp3.tag.getFirst(FieldKey.TITLE) == "") title else mp3.tag.getFirst(FieldKey.TITLE)
        }catch (e: Exception){
            e.stackTrace
        }
        try{
            tmpBpm = mp3.tag.getFirst(FieldKey.BPM).toInt()
        } catch (e: Exception){
            e.stackTrace
        }
        try {
            val imageData = mp3.tag.firstArtwork.binaryData
            val image = ImageIO.read(ByteArrayInputStream(imageData))
            imageFile = File("${tmpTitle}.png")
            ImageIO.write(image, "png", imageFile)
            tmpAlbumImage = imageFile
        } catch (e: Exception){
            e.stackTrace
        }
        val uuid: String = UUID.randomUUID().toString()

        //추출 값
        println("======---title:${tmpTitle}, artist:${tmpArtist} bpm:${tmpBpm}, image: ${tmpAlbumImage}")

        //albumImage 저장
        val albumImageUrl = uploadAlbumImage(tmpAlbumImage, tmpTitle, tmpBpm)
        //song 파일 저장
        val songUrl = uploadSong(song, tmpTitle, tmpBpm)
        tmpAlbumImage.delete()

        songJpaRepository.saveBpmSong(tmpTitle, tmpArtist, albumImageUrl, tmpBpm, uuid, songUrl)
        return SongResponseDto(uuid, tmpTitle, tmpArtist, albumImageUrl, songUrl)
    }

    fun getCorrectUUIDSong(uuid: String): SongResponseDto {
        val song = songJpaRepository.getCorrectUUIDSong(uuid)
        song?.let {
            return convertDto(song)
        }
        throw RuntimeException("해당 uuid로 조회가 불가능합니다.")
    }

    private fun uploadAlbumImage(albumImage: File, title: String, bpm: Int): String {
        return s3UploaderService.uploadAlbumImage(albumImage, title, bpm)
    }

    private fun uploadSong(song: MultipartFile, title: String, bpm: Int): String {
        return s3UploaderService.uploadSongFile(song, title, bpm)
    }


    private fun convertDto(song: SongEntity): SongResponseDto {
        return SongResponseDto(song.uuid, song.title, song.artist, song.albumImage, song.downloadUrl)
    }
}