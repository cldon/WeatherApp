package hu.ait.weatherinfo.data

import android.arch.persistence.room.*
import android.arch.persistence.room.Dao



@Dao
interface WeatherDAO {
    @Query("SELECT * FROM weather")
    fun getAllWeathers(): List<Weather>

    @Insert
    fun insertWeather(todo: Weather): Long

    @Delete
    fun deleteWeather(todo: Weather)

    @Query("DELETE FROM weather")
    fun deleteAll()
}