package com.example.murun.infrastructure.aws

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@Service
class S3UploaderServiceImpl constructor(private val s3Client: AmazonS3,
                                        @Value("\${murun.s3.bucketName}") private val bucket: String,
                                        @Value("\${murun.s3.bucket.base}") private val basePath: String): S3UploaderService {
    override fun getBpmFile(bpm: Int) {
        val objectListing = s3Client.listObjects(bucket, "bpm$bpm")
        println(objectListing)

    }

    override fun uploadBpmFile(file: MultipartFile, title: String, bpm: String) : String{
        val metadata = ObjectMetadata()
        metadata.contentType = file.contentType
        metadata.contentLength = file.size
        var newFileName: String = bpm + "bpm/" + title
        try{
            val request = PutObjectRequest(bucket, newFileName, file.inputStream, metadata)
            s3Client.putObject(request)
        } catch (e: IOException){
            throw e
        }
        return basePath + newFileName
    }

}