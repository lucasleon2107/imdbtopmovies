package me.lucasleon.imdbtopmovies.services

import me.lucasleon.imdbtopmovies.models.Movie
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
                  } as MutableList<Movie>
        }

        if (!star.isNullOrBlank()) {
            movies = movies
                    .filter {
                        it.stars.joinToString().contains(star, ignoreCase = true)
                    } as MutableList<Movie>

        }

        return movies.map { it.name }
    }
}