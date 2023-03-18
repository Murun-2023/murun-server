package com.example.murun.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class MusicController {

    @GetMapping("/test")
    @ResponseBody
    fun findMusic(): String{
        return "hello";
    }
}