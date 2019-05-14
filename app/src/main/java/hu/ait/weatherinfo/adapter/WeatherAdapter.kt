package hu.ait.weatherinfo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.ait.weatherinfo.ScrollingActivity
import hu.ait.weatherinfo.data.AppDatabase
import hu.ait.weatherinfo.data.Weather
import hu.ait.weatherinfo.touch.WeatherTouchHelperCallback
import kotlinx.android.synthetic.main.weather_row.view.*
import java.util.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.ViewHolder>, WeatherTouchHelperCallback {

    var weatherItems = mutableListOf<Weather>()

    private val context: Context


    constructor(context: Context, listWeathers: List<Weather>) : super() {
        this.context = context
        weatherItems.addAll(listWeathers)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val weatherRowView = LayoutInflater.from(context).inflate(
            hu.ait.weatherinfo.R.layout.weather_row, viewGroup, false
        )
        return ViewHolder(weatherRowView)
    }

    override fun getItemCount(): Int {
        return weatherItems.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val weather = weatherItems.get(position)

        viewHolder.tvItem.text = weather.city

        viewHolder.btnDelete.setOnClickListener {
            deleteWeather(viewHolder.adapterPosition)
        }

        viewHolder.btnView.setOnClickListener {

        }

    }


    fun addWeather(weather: Weather) {
        weatherItems.add(weather)
        notifyItemInserted(weatherItems.lastIndex)
    }

    fun deleteWeather(deletePosition: Int) {
        Thread {
            AppDatabase.getInstance(context).weatherDao().deleteWeather(weatherItems.get(deletePosition))

            (context as ScrollingActivity).runOnUiThread {
                weatherItems.removeAt(deletePosition)
                notifyItemRemoved(deletePosition)
            }
        }.start()
    }

    fun deleteAll() {
        Thread{
            AppDatabase.getInstance(context).weatherDao().deleteAll()

            (context as ScrollingActivity).runOnUiThread {
                weatherItems.clear()
                notifyDataSetChanged()
            }
        }.start()
    }

    override fun onDismissed(position: Int) {
        deleteWeather(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(weatherItems, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItem = itemView.tvCity
        var btnDelete = itemView.btnDelete
        var btnView = itemView.btnView
    }
}