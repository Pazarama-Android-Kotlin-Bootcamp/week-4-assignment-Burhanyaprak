package com.burhanyaprak.data.api

import com.burhanyaprak.data.model.WeatherModel
import com.burhanyaprak.data.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("onecall")
    fun getEverythingNews(
        @Query("lat") lat: Double = 40.14,
        @Query("lon") lon: Double = 26.40,
        @Query("exclude") exclude: String = "minutely,hourly,alerts",
        @Query("units") units: String = "metric"
    ): Call<WeatherModel>
}