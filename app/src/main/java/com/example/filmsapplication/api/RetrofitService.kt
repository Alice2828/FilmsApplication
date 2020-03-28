package com.example.filmsapplication.api

import com.example.filmsapplication.model.MovieResponse
import com.google.gson.Gson

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitService {

    //  const val BASE_URL = "https://api.themoviedb.org/3/movie/550?api_key=2f0d69a585b1ec8a833e56046239144b"
    const val BASE_URL = "https://api.themoviedb.org/3/"

    fun getPostApi(): PostApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(PostApi::class.java)
    }
}

interface PostApi {

//    @GET("/movie/popular?api_key=2f0d69a585b1ec8a833e56046239144b")
//    fun getPopularMovieList(): Call<MovieResponse>


    @GET("movie/popular")
    fun getPopularMovieList(@Query("api_key") apiKey: String): Call<MovieResponse>

//    @GET("posts")
//    fun getPostCoroutine(): Deferred<List<Post>>
}