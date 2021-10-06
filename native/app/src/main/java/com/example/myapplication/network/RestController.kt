package com.example.myapplication.network

import com.example.myapplication.Movie
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RestController {

    private object Holder {
        val INSTANCE = RestController()
    }

    companion object{
        val instance: RestController by lazy { Holder.INSTANCE }
    }

    private val moviesApi: MoviesApi

    init {
        val gson: Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://10.0.2.2:5000/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        moviesApi = retrofit.create(MoviesApi::class.java)
    }

    fun getAll(): Observable<List<Movie>> {
        return moviesApi.getAll()
    }
}