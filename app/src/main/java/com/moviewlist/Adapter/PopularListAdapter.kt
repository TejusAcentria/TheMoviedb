package com.moviewlist.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.moviewlist.Acitivties.DetailActivity
import com.moviewlist.Acitivties.MainActivity
import com.moviewlist.R
import com.moviewlist.model.Movie
import com.squareup.picasso.Picasso


class PopularListAdapter(
    private val movies: List<Movie>,
    private val context: Context,
    private val mainActivity: MainActivity

) : RecyclerView.Adapter<PopularListAdapter.MovieViewHolder>() {


    class MovieViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var posterImage: ImageView

        init {
            posterImage = v.findViewById<View>(R.id.images) as ImageView
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recommend_layout_view, parent, false)
        return MovieViewHolder(view)
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Picasso.get()
            .load(context.resources.getString(R.string.image_url) + movies[position]?.poster_path)
            .error(R.drawable.placeholder)
            .into(holder.posterImage)
        var item = movies[position]
        holder.itemView.setOnClickListener {

            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("movie", item)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

        }


    }

    override fun getItemCount(): Int {
        if (movies.size >= 6) {
            return 4
        } else {
            return movies.size
        }

    }

}