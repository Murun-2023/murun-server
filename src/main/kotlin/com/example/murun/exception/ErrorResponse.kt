package com.example.murun.exception


import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime


class ErrorResponse(error: Error) {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    val timestamp: LocalDateTime = LocalDateTime.now()
    val status: Int = error.status.value()
    val error: String = error.status.name
    val code: String = error.name
    val message: String = error.message

}