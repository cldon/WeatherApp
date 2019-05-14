package hu.ait.weatherinfo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import hu.ait.weatherinfo.adapter.CityAdapter
import hu.ait.weatherinfo.data.AppDatabase
import hu.ait.weatherinfo.data.City
import hu.ait.weatherinfo.touch.CityReyclerTouchCallback


import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity(),  AddCityDialog.CityHandler {

    lateinit var cityAdapter : CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            showCityDialog()
        }

        initRecyclerViewFromDB()
    }

    private fun initRecyclerViewFromDB() {
        Thread {
            var cityList = AppDatabase.getInstance(this@ScrollingActivity).cityDao().getAllCities()

            runOnUiThread {

                cityAdapter = CityAdapter(this, cityList)

                recyclerCity.layoutManager = LinearLayoutManager(this)


                recyclerCity.adapter = cityAdapter

                val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
                recyclerCity.addItemDecoration(itemDecoration)

                val callback = CityReyclerTouchCallback(cityAdapter)
                val touchHelper = ItemTouchHelper(callback)
                touchHelper.attachToRecyclerView(recyclerCity)
            }

        }.start()
    }

    private fun showCityDialog() {
        AddCityDialog().show(supportFragmentManager, "TAG_CITY_DIALOG")
    }

    override fun newCityCreated(item: City) {
        Thread {
            var newId = AppDatabase.getInstance(this).cityDao().insertCity(item)

            item.cityId = newId

            runOnUiThread {
               cityAdapter.addCity(item)
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
