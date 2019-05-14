package hu.ait.weatherinfo.touch

interface WeatherTouchHelperCallback {
    fun onDismissed(position: Int)
    fun onItemMoved(fromPosition: Int, toPosition: Int)
}