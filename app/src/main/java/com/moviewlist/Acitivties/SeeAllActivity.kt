package com.moviewlist.Acitivties

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviewlist.APIs.ApiClient
import com.moviewlist.APIs.ApiInterface
import com.moviewlist.Adapter.CompleteListAdapter
import com.moviewlist.R
import com.moviewlist.Utilities.Constants
import com.moviewlist.model.MoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeeAllActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            backImage -> {
                finish()
            }
            searchIcon -> {
                startActivity(Intent(this, SearchActivity::class.java))
            }
        }
    }

    var completeListRecycler: RecyclerView? = null
    var typeValue: String? = null
    var backImage: ImageView? = null
    var listName: TextView? = null
    var searchIcon: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all)
        typeValue = intent.getStringExtra(Constants.nextString)

        setupViews()
        loadCompleteList(typeValue!!)
    }

    private fun setupViews() {

        searchIcon = findViewById(R.id.searchIcon)
        searchIcon!!.setOnClickListener(this)
        listName = findViewById(R.id.listName)
        if (typeValue.equals("popular")) {
            listName!!.text = resources.getString(R.string.popular_movies)
        } else {
            listName!!.text = resources.getString(R.string.top_rated_movies)
        }


        backImage = findViewById(R.id.backImage)
        backImage!!.setOnClickListener(this)

        val viewManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        completeListRecycler = findViewById(R.id.completeListRecycler)
        completeListRecycler.apply {
            this!!.layoutManager = viewManager
            isNestedScrollingEnabled = false
            hasFixedSize()

        }
    }

    private fun loadCompleteList(typeValue: String) {

        val apiService = ApiClient.client.create(ApiInterface::class.java)
        if (typeValue.equals("popular")) {
            val call = apiService.getPopularList(resources.getString(R.string.API_KEY), "popular")
            call.enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    val movies = response.body()!!.results
                    completeListRecycler!!.adapter =
                        CompleteListAdapter(movies, applicationContext)
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                }
            })
        } else {
            val call = apiService.getTopRatedMovies(resources.getString(R.string.API_KEY))
            call.enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    val movies = response.body()!!.results
                    completeListRecycler!!.adapter =
                        CompleteListAdapter(movies, applicationContext)
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                }
            })
        }

    }

}
