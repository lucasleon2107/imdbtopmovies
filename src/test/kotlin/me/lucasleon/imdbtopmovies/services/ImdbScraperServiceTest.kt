package me.lucasleon.imdbtopmovies.services

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ImdbScraperServiceTest {

    @Autowired
    lateinit var imdbScraperService: ImdbScraperService

    @Test
    fun scrapeMovies() {
        assertEquals(1000, imdbScraperService.getAllMovies().size)
    }
}