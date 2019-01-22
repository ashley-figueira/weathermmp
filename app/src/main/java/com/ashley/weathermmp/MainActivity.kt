package com.ashley.weathermmp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_weather.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.ImplicitReflectionSerializer

@ImplicitReflectionSerializer
class MainActivity : AppCompatActivity() {

    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWeather()
    }

    private fun getWeather() {
        progressLayout.visibility = View.VISIBLE

        WeatherApi().fetchWeather({
            uiScope.launch {
                progressLayout.visibility = View.GONE
                weatherCardView.visibility = View.VISIBLE

                weatherTemp.text = it.temperature.toString()
                weatherCondition.text = it.condition
                weatherWind.text = "${it.wind} m/s"
                weatherCity.text = it.city
                weatherWindDirection.text = it.windDirection

                Glide.with(this@MainActivity).load(it.iconUrl).into(weatherIcon)
            }
        }, {
            uiScope.launch {
                weatherCardView.visibility = View.GONE
                progressLayout.visibility = View.GONE
                emptyLayout.visibility = View.VISIBLE

                Log.e(MainActivity::class.java.simpleName, it.localizedMessage)
            }
        })
    }

}
