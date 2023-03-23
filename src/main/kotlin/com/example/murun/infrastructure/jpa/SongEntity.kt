package com.example.murun.infrastructure.jpa

import javax.persistence.*

@Entity
@Table(name = "song")
data class SongEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val bpm: Int,
    val downloadUrl: String
)