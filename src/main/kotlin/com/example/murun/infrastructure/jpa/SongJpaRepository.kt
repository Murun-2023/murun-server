package com.example.murun.infrastructure.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface SongJpaRepository : JpaRepository<SongEntity, Int> {
}