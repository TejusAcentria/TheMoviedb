package com.moviewlist.model

import com.google.gson.annotations.SerializedName

class TrailerResponse {

    @SerializedName("id")
    var idTrailer: Int = 0
        private set
    @SerializedName("results")
    val results: List<Trailer>? = null

    fun seIdTrailer(id_trailer: Int) {
        this.idTrailer = id_trailer
    }
}
