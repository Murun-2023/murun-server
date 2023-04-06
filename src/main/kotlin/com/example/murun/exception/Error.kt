package com.example.murun.exception

import org.springframework.http.HttpStatus


enum class Error(var message: String, var status: HttpStatus) {
    NOT_FOUND_SONG("not found song", HttpStatus.NOT_FOUND),

}