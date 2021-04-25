package me.lucasleon.imdbtopmovies.controllers

import me.lucasleon.imdbtopmovies.services.ImdbMovieQueryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MoviesController(
    private val imdbMovieQueryService: ImdbMovieQueryService
) {
    @GetMapping("/movies")
    fun getImdbMovies(
        @RequestParam(required = false) director: String?,
        @RequestParam(required = false) movieStar: String?
    ): ResponseEntity<List<String>> {
        return ResponseEntity.ok(imdbMovieQueryService.queryMovies(director, movieStar))
    }
}