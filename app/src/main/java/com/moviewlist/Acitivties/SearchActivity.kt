package com.moviewlist.Acitivties

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviewlist.APIs.ApiClient
import com.moviewlist.APIs.ApiInterface
import com.moviewlist.Adapter.CompleteListAdapter
import com.moviewlist.R
import com.moviewlist.model.MoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            backImageButton -> {
                finish()
            }
        }
    }

    var searchListRecycler: RecyclerView? = null
    var searchEdit: EditText? = null
    var backImageButton: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupViews()

    }

    private fun setupViews() {
        val viewManager = GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        searchListRecycler = findViewById(R.id.searchListRecycler)
        searchListRecycler.apply {
            this!!.layoutManager = viewManager
            isNestedScrollingEnabled = false
            hasFixedSize()
        }


        backImageButton = findViewById(R.id.backImage)
        backImageButton!!.setOnClickListener(this)

        searchEdit = findViewById(R.id.searchEdit)
        searchEdit!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    var searchString = s.toString()
                    loadSearchList(searchString)
                } catch (e: Exception) {
                    searchEdit!!.hint = resources.getString(R.string.searchMovie)
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("chekc", " " + s)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("chekc", " " + s)
            }

        })
    }

    private fun loadSearchList(searchString: String) {
        val apiService = ApiClient.client.create(ApiInterface::class.java)
        val call = apiService.getSearchList(searchString, resources.getString(R.string.API_KEY))
        call.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()!!.results
                    searchListRecycler!!.adapter =
                        CompleteListAdapter(movies, applicationContext)
                }

            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
            }
        })


    }
}
