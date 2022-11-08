package com.example.deviceconnector.endpoint

import com.example.deviceconnector.model.WeatherModel
import retrofit2.http.*

/**
 * REST API access points
 */

interface ApiService {

    @GET("weather?units=metric")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") appId: String,
    ): WeatherModel


}