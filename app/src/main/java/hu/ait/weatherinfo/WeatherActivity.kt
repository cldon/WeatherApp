package hu.ait.weatherinfo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import hu.ait.weatherinfo.adapter.CityAdapter
import hu.ait.weatherinfo.data.WeatherResult
import hu.ait.weatherinfo.network.WeatherAPI
import kotlinx.android.synthetic.main.activity_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class WeatherActivity : AppCompatActivity() {

    private val HOST_URL = "https://api.openweathermap.org/"
    private val APP_ID = "e589664ee9f8466e8a6dec99ec7bae30"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        if (intent.extras.containsKey(CityAdapter.CITY_NAME)){
            val name = intent.getStringExtra(CityAdapter.CITY_NAME)
//            val grade = intent.getStringExtra(MainActivity.KEY_GRADE)

            getCurrentValues(name)
        }
    }

    private fun getCurrentValues(name: String) {
        var retrofit = Retrofit.Builder()
            .baseUrl(HOST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var weatherAPI = retrofit.create(WeatherAPI::class.java)

        val call = weatherAPI.getWeatherDetails(name,
            "metric",
            APP_ID
        )
        call.enqueue(object : Callback<WeatherResult> {
            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                val weatherResult = response.body()

                Glide.with(this@WeatherActivity)
                    .load(("https://openweathermap.org/img/w/" +
                                response.body()?.weather?.get(0)?.icon
                                + ".png"))
                    .into(ivWeather)


                tvCoords.text = weatherResult?.coord?.lat.toString() + ", " + weatherResult?.coord?.lon.toString()
                tvWeather.text = weatherResult?.weather?.get(0)?.main?.toString()
                tvDescription.text = weatherResult?.weather?.get(0)?.description?.toString()
                tvTemperature.text = weatherResult?.main?.temp.toString()
                tvPressure.text =  weatherResult?.main?.pressure.toString()
                tvHumidity.text = weatherResult?.main?.humidity.toString()
                tvMinTemp.text = weatherResult?.main?.temp_min?.toString()
                tvMaxTemp.text = weatherResult?.main?.temp_max?.toString()

            }
            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                System.out.println("Request failed")
            }
        })

    }

}
