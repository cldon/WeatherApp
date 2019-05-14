package hu.ait.weatherinfo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.ait.weatherinfo.ScrollingActivity
import hu.ait.weatherinfo.data.AppDatabase
import hu.ait.weatherinfo.data.City
import hu.ait.weatherinfo.touch.CityTouchHelperCallback
import kotlinx.android.synthetic.main.city_row.view.*

import java.util.*

class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>, CityTouchHelperCallback {

    var cityItems = mutableListOf<City>()

    private val context: Context


    constructor(context: Context, listCities: List<City>) : super() {
        this.context = context
        cityItems.addAll(listCities)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val cityRowView = LayoutInflater.from(context).inflate(
            hu.ait.weatherinfo.R.layout.city_row, viewGroup, false
        )
        return ViewHolder(cityRowView)
    }

    override fun getItemCount(): Int {
        return cityItems.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val city = cityItems.get(position)

        viewHolder.tvItem.text = city.city

        viewHolder.btnDelete.setOnClickListener {
            deleteCity(viewHolder.adapterPosition)
        }

        viewHolder.btnView.setOnClickListener {

        }

    }


    fun addCity(city: City) {
        cityItems.add(city)
        notifyItemInserted(cityItems.lastIndex)
    }

    fun deleteCity(deletePosition: Int) {
        Thread {
            AppDatabase.getInstance(context).cityDao().deleteCity(cityItems.get(deletePosition))

            (context as ScrollingActivity).runOnUiThread {
                cityItems.removeAt(deletePosition)
                notifyItemRemoved(deletePosition)
            }
        }.start()
    }

    fun deleteAll() {
        Thread{
            AppDatabase.getInstance(context).cityDao().deleteAll()

            (context as ScrollingActivity).runOnUiThread {
                cityItems.clear()
                notifyDataSetChanged()
            }
        }.start()
    }

    override fun onDismissed(position: Int) {
        deleteCity(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(cityItems, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvItem = itemView.tvCity
        var btnDelete = itemView.btnDelete
        var btnView = itemView.btnView
    }
}