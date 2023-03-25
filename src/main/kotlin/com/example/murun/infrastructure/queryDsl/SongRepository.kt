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
    fun getCorrectBpmSong(bpm: Int): List<SongEntity>{
        return jpaQueryFactory.selectFrom(QSongEntity.songEntity)
                .where(QSongEntity.songEntity.bpm.eq(bpm))
                .fetch()

    }
    //queryDSL insert 라이브러리 문제 때문에 JPA save 사용
    fun saveBpmSong(bpm: Int,uuid: String,  url: String){
        songEntityRepository.save(SongEntity(null,uuid, bpm, url))
    }
}

interface SongEntityRepository : JpaRepository<SongEntity, Int>, JpaSpecificationExecutor<SongEntity> {
}