package com.example.deviceconnector.repository

import android.content.Context
import android.util.Log
import com.example.deviceconnector.util.Constants
import com.example.deviceconnector.endpoint.ApiService
import com.example.deviceconnector.model.MainModel
import com.example.deviceconnector.model.WeatherModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.json.JSONObject
import java.io.IOException

class Repository (
    private val apiService: ApiService,
        ){
    suspend fun getResponse(appContext: Context, fileName: String) = flow<MainModel> {
        val jsonString = appContext.readAssests(fileName)
        val jSonObj = JSONObject(jsonString)
        val guestsItem = Gson().fromJson(jSonObj.toString(), MainModel::class.java)
        flowOf(guestsItem).catch {
            throw Exception("Parsing Issue")
        }.map {
            emit(it)
        }.collect()
    }

    suspend fun getWeather(
        city: String,
        appId : String = Constants.API
    ) = flow<WeatherModel> {
        flowOf(apiService.getWeather(city,appId))
            .catch {
                Log.e("weather","getWeatherResponse Exception = " + it.message)
                throw Exception(it.message)
            }.map {
                Log.e("weather","getWeatherResponse = " + it)
                emit(it)
            }.collect()
    }.flowOn(Dispatchers.IO)


    @Throws(IOException::class)
    fun Context.readAssests(fileName: String): String {
        val input = assets.open(fileName)
        val size = input.available()
        val buffer = ByteArray(size)
        input.read(buffer)
        input.close()
        return String(buffer, Charsets.UTF_8)
    }
}