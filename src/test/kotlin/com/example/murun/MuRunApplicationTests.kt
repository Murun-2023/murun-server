package com.example.murun

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MuRunApplicationTests {

    @Test
    fun test(){
        val test = "3:04".split(":")
        println("first:${test[0].toLong() * 60}")
        println("second:${test[1].toLong() }")
        println((test[0].toLong() * 60 + (test[1].toLong())) * 1000L)
    }
}
