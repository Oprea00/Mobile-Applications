package com.example.myapplication.network

import com.example.myapplication.Movie
import retrofit2.http.GET
import retrofit2.Call
import java.util.*

interface MoviesApi {
    @GET("/movies")
    fun getAll(): Observable<List<Movie>>
}