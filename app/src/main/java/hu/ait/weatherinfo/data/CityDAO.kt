package hu.ait.weatherinfo.data

import android.arch.persistence.room.*
import android.arch.persistence.room.Dao


@Dao
interface CityDAO {
    @Query("SELECT * FROM cities")
    fun getAllCities(): List<City>

    @Insert
    fun insertCity(city: City): Long

    @Delete
    fun deleteCity(city: City)

    @Query("DELETE FROM cities")
    fun deleteAll()
}