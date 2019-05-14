package hu.ait.weatherinfo.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey(autoGenerate = true) var weatherId : Long?,
    @ColumnInfo(name = "category") var city: String
) : Serializable