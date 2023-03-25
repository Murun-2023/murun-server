package com.example.murun.infrastructure.queryDsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class SongRepository(
        private val jpaQueryFactory: JPAQueryFactory
) {
    fun getCorrectBpmSong(bpm: Int): List<SongEntity>{
        return jpaQueryFactory.selectFrom(QSongEntity.songEntity)
                .where(QSongEntity.songEntity.bpm.eq(bpm))
                .fetch()

    }
}