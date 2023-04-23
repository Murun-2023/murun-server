package com.example.murun.infrastructure.queryDsl

import javax.persistence.*

@Entity
@Table(name = "song")
data class SongEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,
        val title: String,
        val artist: String,
        val time: Long,
        val albumImage: String,
        val uuid: String,
        val bpm: Int,
        val downloadUrl: String
) {
    class SongEntityBuilder {
        var title: String? = null
        var artist: String? = null
        var time: Long? = null
        var albumImage: String? = null
        var uuid: String? = null
        var bpm: Int? = null
        var url: String? = null

        fun title(title: String) = apply { this.title = title }
        fun artist(artist: String) = apply { this.artist = artist }
        fun albumImage(albumImage: String) = apply { this.albumImage = albumImage }
        fun uuid(uuid: String) = apply { this.uuid = uuid }
        fun bpm(bpm: Int) = apply { this.bpm = bpm }
        fun url(url: String) = apply { this.url = url }
        fun time(time: Long) = apply { this.time = time }

        fun build() = SongEntity(null, title!!, artist!!, time!!, albumImage!!, uuid!!, bpm!!, url!!)
    }

    companion object {
        fun builder() = SongEntityBuilder()
    }
}