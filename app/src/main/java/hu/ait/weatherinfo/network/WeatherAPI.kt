package hu.ait.weatherinfo.network

import retrofit2.Call
import hu.ait.weatherinfo.data.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather")
    fun getWeatherDetails(
        @Query("q") city: String,
        @Query("units") units: String,
        @Query("appid") appid: String
    ): Call<WeatherResult>
}
