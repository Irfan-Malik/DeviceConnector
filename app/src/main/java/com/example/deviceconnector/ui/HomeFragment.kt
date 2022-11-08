package com.example.deviceconnector.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.deviceconnector.MainActivity
import com.example.deviceconnector.R
import com.example.deviceconnector.adapter.DashBoardAdapter
import com.example.deviceconnector.conditioner.ConditionarActivity
import com.example.deviceconnector.databinding.HomeLayoutBinding
import com.example.deviceconnector.model.IOT
import com.example.deviceconnector.ui.bulb.BulbFragment
import com.example.deviceconnector.ui.camera.CameraFragment
import com.example.deviceconnector.ui.news.NewsActivity
import com.example.deviceconnector.ui.power.PowerPlugFragment
import com.example.deviceconnector.ui.thermostate.ThermostateFragment
import com.example.deviceconnector.util.Constants
import com.example.deviceconnector.util.FragmentStack
import com.example.deviceconnector.util.LocationClass
import com.example.deviceconnector.viewModel.DashBoardViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment(), OnMapReadyCallback {

    lateinit var binding: HomeLayoutBinding
    val list: List<IOT> = arrayListOf()
    var on: String = "On"
    var off: String = "Off"
    private val pERMISSION_ID = 42
    var locationClass: LocationClass? = null
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    var currentLocation: LatLng = LatLng(20.5, 78.9)
    private val viewModel: DashBoardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWidgets()
    }

    val adapter: DashBoardAdapter by lazy {
        DashBoardAdapter(requireContext(), list)
    }

    private fun initWidgets() {
        checkSelectedState()
        setCamera()
        setWatch()
        setConditioner()
        setBulb()
        setNews()
        setThermostate()
        setWeather()
        setCurtains()
        setPlugs()
        getLastLocation()
        weatherRequests()
    }


    private fun weatherRequests() {
        var city = if(!MainActivity.LocationAddress.city.isNullOrEmpty()) MainActivity.LocationAddress.city else "Islamabad"
        viewModel.getWeatherResponse(city)
        observer()
    }

    private fun observer() {
        viewModel.weatherLiveData.observe(viewLifecycleOwner) {

            var cityName = it.data?.name ?: "Islamabad"
            val temp = it.data?.main?.temp ?: "0"
            binding.layoutWeather.location.text = "$cityName,${it.data?.sys?.country}"
            binding.layoutWeather.description.text = it.data?.weather?.get(0)?.main ?: "Shinny"
            binding.layoutWeather.weather.text = "$temp°C"
        }
    }


    private fun setWatch() {
        binding.smartLayout.radioSmartWatch.setOnCheckedChangeListener { _, isChecked ->
            binding.smartLayout.txtOnOffSmartWatch.text = if (isChecked) on else off
            if(isChecked) viewModel.enabledWidget.value?.add(Constants.WATCH) else viewModel.enabledWidget.value?.remove(Constants.WATCH)
        }
        binding.smartLayout.layoutSmartWidget.setOnClickListener {
            if(binding.smartLayout.radioSmartWatch.isChecked) {
                changeFragment(SmartWatchDashBoardFragment(), "SmartWatchFragment")

            }else{
                Toast.makeText(requireActivity(), "Please check Radio Button", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setCamera() {
        binding.cameraLayout.radioCamera.setOnCheckedChangeListener { _, isChecked ->
            binding.cameraLayout.txtOnOffCamera.text = if (isChecked) on else off
            if(isChecked) viewModel.enabledWidget.value?.add(Constants.CAMERA) else viewModel.enabledWidget.value?.remove(Constants.CAMERA)
        }
        binding.cameraLayout.layoutCamera.setOnClickListener {
            if(binding.cameraLayout.radioCamera.isChecked) {
                changeFragment(CameraFragment(), "CameraFragment")
            }
            else{
                Toast.makeText(requireActivity(), "Please check Radio Button", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setConditioner() {
        binding.layoutConditioner.radioConditionar.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutConditioner.txtOnOffConditionar.text = if (isChecked) on else off
            if(isChecked) viewModel.enabledWidget.value?.add(Constants.CONDITIONAR) else viewModel.enabledWidget.value?.remove(Constants.CONDITIONAR)
        }
        binding.layoutConditioner.conditionarLayout.setOnClickListener {
            if(binding.layoutConditioner.radioConditionar.isChecked) {
                startActivity(Intent(requireActivity(), ConditionarActivity::class.java))
            } else{
                Toast.makeText(requireActivity(), "Please check Radio Button", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setNews() {
        binding.layoutNews.newsLayout.setOnClickListener {
            startActivity(Intent(requireActivity(),NewsActivity::class.java))
        }
    }

    private fun setPlugs() {
        binding.layoutPower.radioPlugs.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutPower.txtOnOffPlugs.text = if (isChecked) on else off
            if(isChecked) viewModel.enabledWidget.value?.add(Constants.PLUG) else viewModel.enabledWidget.value?.remove(Constants.PLUG)
        }
        binding.layoutPower.powerLayout.setOnClickListener {
            if(binding.layoutPower.radioPlugs.isChecked){
                changeFragment(PowerPlugFragment(), "PowerFragment")
            }
            else{
                Toast.makeText(requireActivity(), "Please check Radio Button", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setThermostate() {
        binding.layoutThermostate.radioThermostat.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutThermostate.txtOnOffThermostat.text = if (isChecked) on else off
            if(isChecked) viewModel.enabledWidget.value?.add(Constants.THERMOSTAT) else viewModel.enabledWidget.value?.remove(Constants.THERMOSTAT)
        }
        binding.layoutThermostate.thermostateLayout.setOnClickListener {
            if(binding.layoutThermostate.radioThermostat.isChecked) {
                changeFragment(ThermostateFragment(), "ThermoStateFragment")
            }else{
                Toast.makeText(requireActivity(), "Please check Radio Button", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setBulb() {
        binding.layoutBulb.radioBulb.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutBulb.txtOnOffBulb.text = if (isChecked) on else off
            if(isChecked) viewModel.enabledWidget.value?.add(Constants.BULB) else viewModel.enabledWidget.value?.remove(Constants.BULB)
        }
        binding.layoutBulb.bulbLayout.setOnClickListener {
            if(binding.layoutBulb.radioBulb.isChecked) {
                changeFragment(BulbFragment(), "BulbFragment")
            } else{
                Toast.makeText(requireActivity(), "Please check Radio Button", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkSelectedState(){
        var list = viewModel.getMainList(requireContext())
        list?.let {
            viewModel.enabledWidget.value = it as ArrayList<String>

            for (element in it){
                when(element){
                    Constants.WATCH ->{
                        binding.smartLayout.radioSmartWatch.isChecked = true
                    }
                    Constants.CAMERA ->{
                        binding.cameraLayout.radioCamera.isChecked = true
                    }
                    Constants.CONDITIONAR ->{
                        binding.layoutConditioner.radioConditionar.isChecked = true
                    }
                    Constants.BULB ->{
                        binding.layoutBulb.radioBulb.isChecked = true
                    }
                    Constants.PLUG ->{
                        binding.layoutPower.radioPlugs.isChecked = true
                    }
                    Constants.THERMOSTAT ->{
                        binding.layoutThermostate.radioThermostat.isChecked = true
                    }
                }
            }
        }
    }

    private fun setCurtains() {
        binding.layoutCurtain.radioCurtains.setOnCheckedChangeListener { _, isChecked ->
            binding.layoutCurtain.txtOnOffCurtains.text = if (isChecked) on else off
        }
        binding.layoutCurtain.layoutCurtains.setOnClickListener {

        }
    }

    private fun setWeather() {
        binding.layoutWeather.weatherLayout.setOnClickListener {
            changeFragment( WeatherFragment(), "WeatherFragment")
        }
        binding.layoutWeather.location.text = MainActivity.LocationAddress.city ?: "Islamabad"
    }

    fun changeFragment(fragment: Fragment,tag : String){
        FragmentStack.addFragmentToContainer(
            R.id.container,
            requireActivity().supportFragmentManager, fragment, tag
        )
    }


    override fun onMapReady(p0: GoogleMap) {

    }

    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        currentLocation = LatLng(location.latitude, location.longitude)
                        val geocoder = Geocoder(requireActivity(), Locale.getDefault())
                        val addresses: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1) as List<Address>

                        MainActivity.LocationAddress.country = addresses[0].countryName
                        MainActivity.LocationAddress.state = addresses[0].adminArea
                        MainActivity.LocationAddress.countryCode = addresses[0].countryCode
                        MainActivity.LocationAddress.city = addresses[0].locality
                        weatherRequests()
                    }
                }
            } else {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.e("requestCode","RequestGranted = " + grantResults)
        if (requestCode == pERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
                weatherRequests()
            }
        }
    }


    @SuppressLint("MissingPermission")
    fun requestNewLocationData() {
        val mLocationRequest = com.google.android.gms.location.LocationRequest()
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1


        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation!!
            currentLocation = LatLng(mLastLocation.latitude, mLastLocation.longitude)

            val geocoder = Geocoder(requireActivity(), Locale.getDefault())
            val addresses: List<Address> =
                geocoder.getFromLocation(mLastLocation.latitude, mLastLocation.longitude, 1) as List<Address>

            val maxAddressLine: Int = addresses[0].getMaxAddressLineIndex()

            MainActivity.LocationAddress.country = addresses[0].countryName
            MainActivity.LocationAddress.state = addresses[0].adminArea
            MainActivity.LocationAddress.countryCode = addresses[0].countryCode
            MainActivity.LocationAddress.city = addresses[0].locality
        }
    }

    // Check if location permissions are
    // granted to the application
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    // Request permissions if not granted before
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            pERMISSION_ID
        )
    }



}