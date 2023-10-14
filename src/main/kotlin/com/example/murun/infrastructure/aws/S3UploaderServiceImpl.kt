package com.example.murun.infrastructure.aws

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException

@Service
class S3UploaderServiceImpl constructor(private val s3Client: AmazonS3,
                                        @Value("\${murun.s3.bucketName}") private val bucket: String,
                                        @Value("\${murun.s3.bucket.base}") private val basePath: String,
                                        @Value("\${murun.s3.input.base}") private val input: String,
                                        @Value("\${murun.s3.output.base}") private val output: String) : S3UploaderService {
    val albumBase = "album/"

    override fun getBpmFile(bpm: Int) {
        val objectListing = s3Client.listObjects(bucket, "bpm$bpm")
        println(objectListing)

    }

    override fun uploadAlbumImage(file: File, title: String, bpm: Int): String {
        val newFileName: String = input + bpm.toString() + "bpm/" + albumBase + title
        try {
            val request = PutObjectRequest(bucket, newFileName, file)
            s3Client.putObject(request)
        } catch (e: IOException) {
            throw e
        }
        return basePath + newFileName
    }

    override fun uploadSongFile(file: MultipartFile, title: String, bpm: Int): String {
        val metadata = ObjectMetadata()
        metadata.contentType = file.contentType
        metadata.contentLength = file.size
        var newFileName: String = input + bpm.toString() + "bpm/" + title
        try {
            val request = PutObjectRequest(bucket, newFileName, file.inputStream, metadata)
            s3Client.putObject(request)
        } catch (e: IOException) {
            throw e
        }
        return basePath + (output + bpm.toString() + "bpm/" + title).replace(" ","+") + "/Hls.m3u8";

    }

}