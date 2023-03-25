package com.example.murun.domain

import com.example.murun.infrastructure.queryDsl.SongEntity
import com.example.murun.infrastructure.queryDsl.SongRepository
import org.springframework.stereotype.Service

@Service
class SongService(private val songJpaRepository: SongRepository) {

    fun getCorrectBpmSong(bpm: Int): List<SongResponseDto>{
        return songJpaRepository.getCorrectBpmSong(bpm)
                .map{
                    convertDto(it)
                }.toList()
    }

    private fun convertDto(songEntity: SongEntity): SongResponseDto{
        return SongResponseDto(songEntity.uuid, songEntity.downloadUrl)
    }
}