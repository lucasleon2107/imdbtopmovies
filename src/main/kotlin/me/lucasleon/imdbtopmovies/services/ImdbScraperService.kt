package me.lucasleon.imdbtopmovies.services

import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.extractBlocking
import it.skrape.fetcher.skrape
import it.skrape.selects.Doc
import it.skrape.selects.DocElement
import it.skrape.selects.eachText
import it.skrape.selects.html5.a
import it.skrape.selects.html5.div
import it.skrape.selects.html5.h3
import it.skrape.selects.html5.p
import me.lucasleon.imdbtopmovies.models.Movie
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ImdbScraperService {

    @Cacheable("movies")
    fun getAllMovies(): MutableList<Movie> {
        val allMovies = mutableListOf<Movie>()

        (1..1000 step 250).forEach { i ->
            allMovies.addAll(scrapeMovies(i))
        }

        return allMovies
    }

    fun scrapeMovies(start: Int): List<Movie> {
        val movies = mutableListOf<Movie>()

        skrape(HttpFetcher) {
            request {
                url =
                    "https://www.imdb.com/search/title/?groups=top_1000&sort=user_rating,desc&count=250&start=$start&ref_=adv_nxt"
            }
            extractBlocking {
                htmlDocument {
                    scrapeMovieName().map {
                        movies.add(Movie(name = it))
                    }

                    scrapeDirectors().forEachIndexed { index, element ->
                        movies[index].director = element
                    }

                    scrapeMovieStars().forEachIndexed { index, element ->
                        movies[index].stars = element
                    }
                }
            }
        }

        return movies
    }

    private fun Doc.scrapeMovieName(): List<String> {
        return div {
            findAll {
                withClass = "lister-item"
                h3 {
                    findAll {
                        a {
                            findAll {
                                eachText
                            }
                        }
                    }
                }
            }
        }
    }

    private fun Doc.scrapeDirectors(): List<String> {
        val links = scrapeHrefs()

        return links.filter { "dr_0" in it.attribute("href") }.eachText
    }

    private fun Doc.scrapeMovieStars(): List<List<String>> {
        val links = scrapeHrefs()

        return links.filter { "st_" in it.attribute("href") }.eachText
            .withIndex()
            .groupBy { it.index / 4 }
            .map { it.value.map { star -> star.value } }
    }

    private fun Doc.scrapeHrefs(): List<DocElement> {
        return div {
            withClass = "lister-item-content"
            findAll {
                p {
                    findAll {
                        a {
                            findAll {
                                this
                            }
                        }
                    }
                }
            }
        }
    }
}