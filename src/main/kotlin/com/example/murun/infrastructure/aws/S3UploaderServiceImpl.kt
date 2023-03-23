package com.example.murun.infrastructure.aws

import com.amazonaws.services.s3.AmazonS3
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class S3UploaderServiceImpl constructor(private val s3Client: AmazonS3,
                                        @Value("\${aws.s3.bucketName}") private val bucket: String,
                                        @Value("\${bpm.s3.bucket.base}") private val basePath: String): S3UploaderService {
    override fun getBpmFile(bpm: Int) {
        val objectListing = s3Client.listObjects(bucket, "bpm$bpm")
        println(objectListing)

    }

}