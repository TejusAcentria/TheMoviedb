package com.moviewlist.Acitivties

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviewlist.APIs.ApiClient
import com.moviewlist.APIs.ApiInterface
import com.moviewlist.Adapter.TrailerAdapter
import com.moviewlist.R
import com.moviewlist.model.Movie
import com.moviewlist.model.TrailerResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            backImageButton -> {
                finish()
            }
        }
    }

    var movieBackground: ImageView? = null
    var moviePoster: ImageView? = null
    var backImageButton: ImageView? = null
    var movieName: TextView? = null
    var movieType: TextView? = null
    var movieReview: TextView? = null
    var movieTime: TextView? = null
    var movieReleaseDate: TextView? = null
    var movieOverview: TextView? = null
    var trailerRecyclerview: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setupViews()

        setValues(getMovieObject())

        loadTrailerList()
    }

    private fun loadTrailerList() {
        val apiService = ApiClient.client.create(ApiInterface::class.java)
        val call =
            apiService.getMovieTrailer(getMovieObject().id!!, resources.getString(R.string.API_KEY))
        call.enqueue(object : Callback<TrailerResponse> {
            override fun onResponse(
                call: Call<TrailerResponse>,
                response: Response<TrailerResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()!!.results
                    trailerRecyclerview!!.adapter =
                        TrailerAdapter(applicationContext, movies!!)
                }

            }

            override fun onFailure(call: Call<TrailerResponse>, t: Throwable) {
            }
        })
    }

    private fun setValues(movie: Movie) {
        Picasso.get().load(resources.getString(R.string.image_url) + movie.poster_path)
            .error(R.drawable.placeholder).into(moviePoster)
        Picasso.get().load(resources.getString(R.string.image_url) + movie.backdrop_path)
            .error(R.drawable.placeholder).into(movieBackground)
        movieName!!.text = movie.title
        movieType!!.text = movie.original_title
        movieReview!!.text = "" + movie.vote_count + " Reviews"
        movieReleaseDate!!.text = movie.release_date + " Released"
        movieTime!!.text = "124 mins"
        movieOverview!!.text = movie.overview
    }

    private fun setupViews() {
        val viewManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        movieBackground = findViewById(R.id.movieBackground)
        moviePoster = findViewById(R.id.moviePoster)

        backImageButton = findViewById(R.id.backImageButton)
        backImageButton!!.setOnClickListener(this)
        movieName = findViewById(R.id.movieName)
        movieType = findViewById(R.id.movieType)
        movieReview = findViewById(R.id.movieReview)
        movieTime = findViewById(R.id.movieTime)
        movieReleaseDate = findViewById(R.id.movieReleaseDate)
        movieOverview = findViewById(R.id.movieOverview)
        trailerRecyclerview = findViewById(R.id.trailerRecyclerview)
        trailerRecyclerview.apply {
            this!!.layoutManager = viewManager
            isNestedScrollingEnabled = false
            hasFixedSize()
        }


    }

    private fun getMovieObject(): Movie {
        return intent.getSerializableExtra("movie") as Movie
    }
}
