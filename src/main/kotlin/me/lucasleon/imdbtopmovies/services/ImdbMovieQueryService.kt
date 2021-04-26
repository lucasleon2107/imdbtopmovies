package me.lucasleon.imdbtopmovies.services

import org.springframework.stereotype.Service

@Service
class ImdbMovieQueryService(
    private val imdbScraperService: ImdbScraperService
) {
    fun queryMovies(director: String? = "", star: String? = ""): List<String> {
        var movies = imdbScraperService.getAllMovies()

        if (!director.isNullOrBlank()) {
            movies = movies
                .filter {
                    it.director.contains(director, ignoreCase = true)
                }
        }

        if (!star.isNullOrBlank()) {
            movies = movies
                .filter {
                    it.stars.joinToString().contains(star, ignoreCase = true)
                }

        }

        return movies.map { it.name }
    }
}