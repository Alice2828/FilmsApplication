package com.example.filmsapplication.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmsapplication.DetailActivity
import com.example.filmsapplication.R
import com.example.filmsapplication.model.Movie
import kotlinx.android.synthetic.main.movie_card.view.*

class MoviesAdapter(
    var context: Context,
    var moviesList: List<Movie>? = null
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.movie_card, p0, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = moviesList?.size ?: 0

    override fun onBindViewHolder(p0: MovieViewHolder, p1: Int) {
        p0.bind(moviesList?.get(p1))
    }

    fun clearAll(){
        (moviesList as? ArrayList<Movie>)?.clear()
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        fun bind(post: Movie?) {
            val title = view.findViewById<TextView>(R.id.title)
            val userrating = view.findViewById<TextView>(R.id.userrating)
            val thumbnail = view.findViewById<ImageView>(R.id.thumbnail)


            title.text = post?.original_title
            val vote=post?.vote_average
            userrating.text =vote.toString()

            Glide.with(context)
                .load(post?.getPosterPath())
                .into(thumbnail)
            //                .placeholder(R.drawable.load)

            view.setOnClickListener {
                val intent= Intent(view.context,DetailActivity::class.java)
                intent.putExtra("original_title", post?.original_title)
                intent.putExtra("poster_path", post?.poster_path)
                intent.putExtra("overview", post?.overview)
                intent.putExtra("vote_average", (post?.vote_average).toString())
                intent.putExtra("relase_date", post?.release_date)
                view.context.startActivity(intent)
            }
        }
    }

    interface RecyclerViewItemClick {

        fun itemClick(position: Int, item: Movie)
    }

}