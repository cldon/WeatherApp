package hu.ait.weatherinfo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import hu.ait.weatherinfo.adapter.WeatherAdapter
import hu.ait.weatherinfo.data.AppDatabase
import hu.ait.weatherinfo.data.Weather
import hu.ait.weatherinfo.touch.WeatherReyclerTouchCallback
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity(),  AddWeatherDialog.WeatherHandler {

    lateinit var weatherAdapter : WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            showWeatherDialog()
        }

        initRecyclerViewFromDB()
    }

    private fun initRecyclerViewFromDB() {
        Thread {
            var weatherList = AppDatabase.getInstance(this@ScrollingActivity).weatherDao().getAllWeathers()

            runOnUiThread {

                weatherAdapter = WeatherAdapter(this, weatherList)

                recyclerWeather.layoutManager = LinearLayoutManager(this)


                recyclerWeather.adapter = weatherAdapter

                val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
                recyclerWeather.addItemDecoration(itemDecoration)

                val callback = WeatherReyclerTouchCallback(weatherAdapter)
                val touchHelper = ItemTouchHelper(callback)
                touchHelper.attachToRecyclerView(recyclerWeather)
            }

        }.start()
    }

    private fun showWeatherDialog() {
        AddWeatherDialog().show(supportFragmentManager, "TAG_WEATHER_DIALOG")
    }

    override fun newWeatherCreated(item: Weather) {
        Thread {
            var newId = AppDatabase.getInstance(this).weatherDao().insertWeather(item)

            item.weatherId = newId

            runOnUiThread {
               weatherAdapter.addWeather(item)
            }
        }.start()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
