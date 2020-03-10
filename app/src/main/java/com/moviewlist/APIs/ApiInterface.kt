package com.moviewlist.APIs

import com.moviewlist.model.MoviesResponse
import com.moviewlist.model.TrailerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("discover/movie")
    fun getPopularList(@Query("api_key") apiKey: String, @Query("sort_by") sortby: String): Call<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") apiKey: String): Call<MoviesResponse>

    @GET("movie/{movie_id}/videos")
    fun getMovieTrailer(@Path("movie_id") id: Int, @Query("api_key") apiKey: String): Call<TrailerResponse>

    @GET("search/movie")
    fun getSearchList(@Query("query") serch_key: String, @Query("api_key") apiKey: String): Call<MoviesResponse>
}