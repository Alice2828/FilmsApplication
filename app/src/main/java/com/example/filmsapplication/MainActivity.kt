package com.example.filmsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.filmsapplication.adapter.MoviesAdapter
import com.example.filmsapplication.api.RetrofitService
import com.example.filmsapplication.model.Movie
import com.example.filmsapplication.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    private var postAdapter: MoviesAdapter? = null
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var movieList: List<Movie>

//    private var listener: MoviesListAdapter.ItemClickListener? = null
//    private var fragmentButtonListener: MoviesListAdapter.FragmentButtonListener? = null
//    private var fragmentLikeListener: MoviesListAdapter.FragmentLikeListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        swipeRefreshLayout = findViewById(R.id.main_content)

        swipeRefreshLayout.setOnRefreshListener {
            initViews()

        }
    }


    fun initViews() {
        recyclerView = findViewById(R.id.recycler_view)

        movieList = ArrayList<Movie>()
        postAdapter = MoviesAdapter(this,movieList)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.itemAnimator=DefaultItemAnimator()
        recyclerView.adapter = postAdapter
        postAdapter?.notifyDataSetChanged()

        loadJSON()

    }

    fun loadJSON() {
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                return;
            }
            RetrofitService.getPostApi().getPopularMovieList(BuildConfig.THE_MOVIE_DB_API_TOKEN)
                .enqueue(object : Callback<MovieResponse> {
                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        swipeRefreshLayout.isRefreshing = false
                    }

                    override fun onResponse(
                        call: Call<MovieResponse>,
                        response: Response<MovieResponse>
                    ) {
                        Log.d("My_post_list", response.body().toString())
                        if (response.isSuccessful) {
                            val list = response.body()?.results
                            postAdapter?.moviesList = list
                            postAdapter?.notifyDataSetChanged()
                        }
                        swipeRefreshLayout.isRefreshing = false
                    }
                })


        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT)
        }
    }
}


