package me.lucasleon.imdbtopmovies

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class ImdbTopMoviesApplication

fun main(args: Array<String>) {
    runApplication<ImdbTopMoviesApplication>(*args)
}
