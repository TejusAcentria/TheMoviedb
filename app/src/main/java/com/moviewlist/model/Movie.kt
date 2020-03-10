package com.moviewlist.model

import java.io.Serializable

class Movie : Serializable {

    var poster_path: String? = null
    var adult: Boolean? = null
    var overview: String? = null
    var release_date: String? = null
    var genre_ids: List<Int>? = null
    var id: Int? = null
    var original_title: String? = null
    var original_language: String? = null
    var title: String? = null
    var backdrop_path: String? = null
    var popularity: Double? = null
    var vote_count: Int? = null
    var video: Boolean? = null
    var vote_average: Double? = null
}
