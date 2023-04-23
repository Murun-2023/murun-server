package com.example.murun.infrastructure.queryDsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class SongRepository(
        private val jpaQueryFactory: JPAQueryFactory,
        private val songEntityRepository: SongEntityRepository
) {
    fun getCorrectBpmSong(bpm: Int): List<SongEntity> {
        val range = 3
        return jpaQueryFactory.selectFrom(QSongEntity.songEntity)
                .where(QSongEntity.songEntity.bpm.between(bpm - range, bpm + range))
                .fetch()

    }

    fun getCorrectUUIDSong(uuid: String): SongEntity?{
        return jpaQueryFactory.selectFrom(QSongEntity.songEntity)
                .where(QSongEntity.songEntity.uuid.eq(uuid))
                .fetchOne()
    }

    //queryDSL insert 라이브러리 문제 때문에 JPA save 사용
    fun saveBpmSong(title: String, artist: String, time: Long, albumImage: String,bpm: Int, uuid: String, url: String) {
        val songEntity = SongEntity.builder()
                .title(title)
                .artist(artist)
                .time(time)
                .albumImage(albumImage)
                .uuid(uuid)
                .bpm(bpm)
                .url(url)
                .build()
        songEntityRepository.save(songEntity)
    }
}

interface SongEntityRepository : JpaRepository<SongEntity, Int>, JpaSpecificationExecutor<SongEntity> {
}