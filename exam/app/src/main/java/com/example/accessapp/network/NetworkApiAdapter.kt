package com.example.accessapp.network

import com.example.accessapp.model.Car
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class NetworkApiAdapter private constructor(){
    private object Holder {
        val INSTANCE = NetworkApiAdapter()
    }

    companion object{
        val instance : NetworkApiAdapter by lazy { Holder.INSTANCE }
        const val BASE_URL: String = "http://10.0.2.2:2021/"
        private const val URL_ALL: String = "review"
        private const val URL_POST: String = "vehicle"
        private const val URL_DELETE: String = "vehicle/{id}"
        private const val URL_GET_DRIVERS: String = "all"
        private const val URL_GET_COLORS: String = "colors"
        private const val URL_GET_Vehicles: String = "vehicles/{color}"
    }

    private val accessService: Service

    init {
        // used for parsing JSON string into class object and vice versa
        val gson: Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        // define the URL endpoint with the retrofit builder
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        accessService = retrofit.create(Service::class.java)
    }

    fun getAll(): Observable<List<Car>>{
        return accessService.getAll()
    }

    fun insert(car: Car): Observable<Car>{
        return accessService.insert(car.license!!, car.status!!, car.seats!!, car.driver!!, car.color!!, car.cargo!!)
    }

    fun delete(id: Int): Observable<Car> {
        return accessService.delete(id)
    }

    fun getColors(): Observable<List<String>>{
        return accessService.getColors()
    }

    fun getVehicles(color: String): Observable<List<Car>>{
        return accessService.getVehicles(color)
    }

    fun getDrivers(): Observable<List<Car>>{
        return accessService.getDrivers()
    }



    interface Service{
        @GET(URL_ALL)
        fun getAll(): Observable<List<Car>>

        @GET(URL_GET_DRIVERS)
        fun getDrivers(): Observable<List<Car>>

        @FormUrlEncoded
        @POST(URL_POST)
        fun insert(
            @Field("license") license: String,
            @Field("status") status: String,
            @Field("seats") seats: Int,
            @Field("driver") driver: String,
            @Field("color") color: String,
            @Field("cargo") cargo: Int
        ): Observable<Car>

        @DELETE(URL_DELETE)
        fun delete(@Path("id") id: Int): Observable<Car>



       @GET(URL_GET_COLORS)
        fun getColors(): Observable<List<String>>

        @GET(URL_GET_Vehicles)
        fun getVehicles(@Path("color") color: String): Observable<List<Car>>


    }
}
