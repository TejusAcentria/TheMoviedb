package com.moviewlist.Acitivties

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviewlist.APIs.ApiClient
import com.moviewlist.APIs.ApiInterface
import com.moviewlist.Adapter.PopularListAdapter
import com.moviewlist.R
import com.moviewlist.Utilities.Constants
import com.moviewlist.model.MoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            popularShowAll -> {
                go_to_next_activity("popular")
            }
            topRatedShowAll -> {
                go_to_next_activity("topRated")
            }
        }
    }

    private fun go_to_next_activity(type: String) {
        val intent = Intent(this, SeeAllActivity::class.java)
        intent.putExtra(Constants.nextString, type)
        startActivity(intent)

    }

    var popularRecycler: RecyclerView? = null
    var topRatedRecycler: RecyclerView? = null
    var topRatedShowAll: TextView? = null
    var popularShowAll: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupViews()
        loadPopularList()
        loadTopRatedList()

    }

    private fun setupViews() {
        popularShowAll = findViewById(R.id.popularShowAll)
        popularShowAll!!.setOnClickListener(this)
        topRatedShowAll = findViewById(R.id.topRatedShowAll)
        topRatedShowAll!!.setOnClickListener(this)

        val viewManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        popularRecycler = findViewById(R.id.popularRecycler)
        popularRecycler.apply {
            this!!.layoutManager = viewManager
            isNestedScrollingEnabled = false
            hasFixedSize()

        }

        val viewManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topRatedRecycler = findViewById(R.id.topRatedRecycler)
        topRatedRecycler.apply {
            this!!.layoutManager = viewManager2
            isNestedScrollingEnabled = false
            hasFixedSize()
        }


    }

    private fun loadPopularList() {
        val apiService = ApiClient.client.create(ApiInterface::class.java)
        val call = apiService.getPopularList(resources.getString(R.string.API_KEY), "popularity")
        call.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                val movies = response.body()!!.results
                popularRecycler!!.adapter =
                    PopularListAdapter(movies, applicationContext, this@MainActivity)
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
            }
        })
    }

    private fun loadTopRatedList() {
        val apiService = ApiClient.client.create(ApiInterface::class.java)
        val call = apiService.getTopRatedMovies(resources.getString(R.string.API_KEY))
        call.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                val movies = response.body()!!.results
                topRatedRecycler!!.adapter =
                    PopularListAdapter(movies, applicationContext, this@MainActivity)
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
            }
        })
    }

}
