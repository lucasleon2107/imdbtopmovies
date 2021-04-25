package me.lucasleon.imdbtopmovies.models

data class Movie(
    var name: String = "",
    var director: String = "",
    var stars: List<String> = listOf()
)
