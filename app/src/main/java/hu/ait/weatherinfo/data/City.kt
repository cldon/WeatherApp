package hu.ait.weatherinfo.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cities")
data class City(
    @PrimaryKey(autoGenerate = true) var cityId : Long?,
    @ColumnInfo(name = "category") var city: String
) : Serializable