package com.example.deviceconnector.ui.thermostate
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.deviceconnector.databinding.ThermostateLayoutBinding
import java.util.*


class ThermostateFragment : Fragment() ,SensorEventListener{

    lateinit var binding: ThermostateLayoutBinding
    private var sensorManager: SensorManager? = null
    private var thermometer: Thermometer? = null
    private var temperature = 0
    private var timer: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ThermostateLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    fun setData(){
        sensorManager =  requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        thermometer = binding.thermometer
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        if (sensorEvent?.values?.size!!  > 0) {
            temperature = sensorEvent.values[0].toInt()
            thermometer!!.setCurrentTemp(temperature.toFloat())
            binding.temperature.text = "Temperature : $temperature"
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    private fun loadAmbientTemperature() {
        val sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if (sensor != null) {
            sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
        } else {
            simulateAmbientTemperature()
        }
    }

    private fun simulateAmbientTemperature() {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                temperature = randomNumber()
                if(isAdded) {
                    requireActivity().runOnUiThread(Runnable {
                        thermometer!!.setCurrentTemp(temperature.toFloat())
                        binding.temperature.text = "Temperature : $temperature"
                    })
                }
            }
        }, 0, 3500)
    }

    private fun randomNumber(): Int {
        val min = -10
        val max = 35
        val rand = Random()
        return rand.nextInt(max - min + 1) + min
    }

    private fun unregisterAll() {
        sensorManager!!.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        loadAmbientTemperature()
    }

     override fun onPause() {
        super.onPause()
        unregisterAll()
    }


}