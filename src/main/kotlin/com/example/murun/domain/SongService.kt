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
import java.io.File
import java.lang.RuntimeException
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

    fun addSong(title: String, artist: String, bpm: Int, albumImage: MultipartFile, song: MultipartFile): SongResponseDto {
        //albumImage 저장
        val albumImageUrl = uploadAlbumImage(albumImage, title, bpm)
        //song 파일 저장
        val songUrl = uploadSong(song, title, bpm)
        //추후 추가여부를 위해 남겨놓음 요건이 변경될 가능성이 있음.
        /*
        val file = File(multipartFile.originalFilename)
        file.writeBytes(multipartFile.bytes)

        val mp3: MP3File = AudioFileIO.read(file) as MP3File
        file.delete()

        val title: String = mp3.tag.getFirst(FieldKey.TITLE)
        val bpm: String = mp3.tag.getFirst(FieldKey.BPM)
        val uuid: String = UUID.randomUUID().toString()
        val url = s3UploaderService.uploadBpmFile(multipartFile, title, bpm)
         */
        val uuid: String = UUID.randomUUID().toString()
        songJpaRepository.saveBpmSong(title, artist, albumImageUrl, bpm, uuid, songUrl)
        return SongResponseDto(uuid, title, artist, albumImageUrl, songUrl)
    }

    fun getCorrectUUIDSong(uuid: String): SongResponseDto{
        val song = songJpaRepository.getCorrectUUIDSong(uuid)
        song?.let{
            return convertDto(song)
        }
        throw RuntimeException("해당 uuid로 조회가 불가능합니다.")
    }

    private fun uploadAlbumImage(albumImage: MultipartFile, title: String, bpm: Int): String {
        return s3UploaderService.uploadAlbumImage(albumImage, title, bpm)
    }

    private fun uploadSong(song: MultipartFile, title: String, bpm: Int): String {
        return s3UploaderService.uploadSongFile(song, title, bpm)
    }

    private fun convertDto(song: SongEntity): SongResponseDto {
        return SongResponseDto(song.uuid, song.title, song.artist, song.albumImage, song.downloadUrl)
    }
}