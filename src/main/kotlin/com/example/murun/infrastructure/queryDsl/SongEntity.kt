package com.example.murun.infrastructure.queryDsl

import javax.persistence.*

@Entity
@Table(name = "song")
data class SongEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        val uuid: String,
        val bpm: Int,
        val downloadUrl: String
)