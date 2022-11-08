package com.example.deviceconnector.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.deviceconnector.databinding.WeatherLayoutBinding
import com.example.deviceconnector.model.WeatherModel
import com.example.deviceconnector.viewModel.DashBoardViewModel
import java.text.SimpleDateFormat
import java.util.*

class WeatherFragment : Fragment() {

    lateinit var binding: WeatherLayoutBinding
    private val viewModel: DashBoardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WeatherLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getWeather()
    }

    fun getWeather() {

        observer()
    }

    private fun observer() {
        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            it.data?.let {
                setData(it)
            }
        }
    }

    fun setData(weatherModel: WeatherModel) {

        val updatedAt: Long = weatherModel.dt.toLong()
        val updatedAtText =
            "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                Date(updatedAt * 1000)
            )
        val temp = weatherModel.main.temp.toString() + "°C"
        val tempMin = "Min Temp: " + weatherModel.main.temp_min + "°C"
        val tempMax = "Max Temp: " + weatherModel.main.temp_max + "°C"
        val pressure = weatherModel.main.pressure
        val humidity = weatherModel.main.humidity

        val sunrise: Long = weatherModel.sys.sunrise.toLong()
        val sunset: Long = weatherModel.sys.sunset.toLong()
        val windSpeed = weatherModel.wind.speed
        val weatherDescription = weatherModel.weather[0].description

        val address = weatherModel.name + ", " + weatherModel.sys.country


        binding.address.text = address
        binding.updatedAt.text = updatedAtText.toString()
        binding.status.text = weatherDescription.capitalize()
        binding.temp.text = temp
        binding.tempMin.text = tempMin.toString()
        binding.tempMax.text = tempMax.toString()
        binding.sunrise.text =
            SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise * 1000))
        binding.sunset.text =
            SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))
        binding.wind.text = windSpeed.toString()
        binding.pressure.text = pressure.toString()
        binding.humidity.text = humidity.toString()
    }


}