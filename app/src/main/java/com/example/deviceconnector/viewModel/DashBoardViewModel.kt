package com.example.deviceconnector.viewModel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deviceconnector.R
import com.example.deviceconnector.model.PowerPlug
import com.example.deviceconnector.model.WeatherModel
import com.example.deviceconnector.model.bulb.BulbModel
import com.example.deviceconnector.model.camera.CameraModel
import com.example.deviceconnector.model.smart_watch.SmartWatchModel
import com.example.deviceconnector.repository.Repository
import com.example.deviceconnector.util.Resources
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Type
import javax.inject.Inject


@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var weatherLiveData: MutableLiveData<Resources<WeatherModel>> = MutableLiveData()
    var cameraLiveData: MutableLiveData<ArrayList<CameraModel>> = MutableLiveData()
    var bulbLiveData: MutableLiveData<ArrayList<BulbModel>> = MutableLiveData()
    var powerLiveData: MutableLiveData<ArrayList<PowerPlug>> = MutableLiveData()
     var selectedCamera : CameraModel? = null
     var selectedBulb : BulbModel? = null
     var selectedPower : PowerPlug? = null
    var selectedPostion : Int = 0
    var isPowerPlug : Boolean = false
    var isDataUpdated : MutableLiveData<Boolean> = MutableLiveData()
    var enabledWidget : MutableLiveData<ArrayList<String>> = MutableLiveData()


    fun getWeatherResponse(city: String) {
        viewModelScope.launch {
            repository.getWeather(city).onStart {
                withContext(Dispatchers.Main) {
                    weatherLiveData.value = Resources(
                        Resources.Status.LOADING,
                        null,
                        ""
                    )
                }
            }.catch {
                withContext(Dispatchers.Main) {
                    weatherLiveData.value = Resources(
                        Resources.Status.ERROR,
                        null,
                        it.message.toString()
                    )
                }

            }.collect {
                withContext(Dispatchers.Main) {
                    weatherLiveData.value = Resources(
                        Resources.Status.SUCCESS,
                        it,
                        "successfully"
                    )
                }
            }
        }
    }

    fun getCameraData() {
        var array : ArrayList<CameraModel> = arrayListOf()
        var model  = CameraModel(name = "Living Room", image = R.drawable.living)
        array.add(model)
        model = CameraModel(name = "BedRoom", image = R.drawable.bedroom)
        array.add(model)
        model = CameraModel(name = "Kitchen", image = R.drawable.kitchen)
        array.add(model)
        model = CameraModel(name = "Bath Room", image = R.drawable.baathroom)
        array.add(model)
        cameraLiveData.value = array

    }

    fun getCameraItem(pos:Int){
        selectedCamera = cameraLiveData.value?.get(pos)
        selectedPostion = pos
    }

    fun getBulbData() {
        var array : ArrayList<BulbModel> = arrayListOf()
        var model  = BulbModel(name = "Living Room", image = R.drawable.living,bulbcolor = 1)
        array.add(model)
        model = BulbModel(name = "BedRoom", image = R.drawable.bedroom ,bulbcolor = 2)
        array.add(model)
        model = BulbModel(name = "Kitchen", image = R.drawable.kitchen ,bulbcolor = 3)
        array.add(model)
        model = BulbModel(name = "Bath Room", image = R.drawable.baathroom ,bulbcolor = 4)
        array.add(model)
        bulbLiveData.value = array

    }
    fun getPowerData()  {
        var array : ArrayList<PowerPlug> = arrayListOf()
        var model  = PowerPlug(name = "Home", image = R.drawable.home)
        array.add(model)
        model = PowerPlug(name = "LCD", image = R.drawable.lcd )
        array.add(model)
        model = PowerPlug(name = "Fan", image = R.drawable.fan)
        array.add(model)
        model = PowerPlug(name = "Lamp", image = R.drawable.lamp ,)
        array.add(model)
        powerLiveData.value = array

    }
    fun getSmartWatchData() : ArrayList<SmartWatchModel>  {
        var array : ArrayList<SmartWatchModel> = arrayListOf()
        var model  = SmartWatchModel(name = "Phone", image = R.drawable.phone)
        array.add(model)
        model = SmartWatchModel(name = "Skype", image = R.drawable.skype )
        array.add(model)
        model = SmartWatchModel(name = "Linkedin", image = R.drawable.linkedin )
        array.add(model)
        model = SmartWatchModel(name = "Facebook", image = R.drawable.facebook )
        array.add(model)
        model = SmartWatchModel(name = "Setting", image = R.drawable.setting )
        array.add(model)
        model = SmartWatchModel(name = "Recording", image = R.drawable.recording )
        array.add(model)
        model = SmartWatchModel(name = "Youtube", image = R.drawable.youtube )
        array.add(model)
        model = SmartWatchModel(name = "Twitter", image = R.drawable.twitter )
        array.add(model)
        model = SmartWatchModel(name = "Alarm", image = R.drawable.alarm )
        array.add(model)
        model = SmartWatchModel(name = "Browser", image = R.drawable.chrome )
        array.add(model)
        model = SmartWatchModel(name = "Google", image = R.drawable.google )
        array.add(model)
        model = SmartWatchModel(name = "Camera", image = R.drawable.smartcamera )
        array.add(model)
        model = SmartWatchModel(name = "Play Store", image = R.drawable.playstore )
        array.add(model)
        model = SmartWatchModel(name = "Bluetooth", image = R.drawable.bluetooth )
        array.add(model)
        model = SmartWatchModel(name = "Search", image = R.drawable.search )
        array.add(model)
        model = SmartWatchModel(name = "Calculator", image = R.drawable.calculator )
        array.add(model)
        return array
    }

    fun getBulbItem(pos:Int){
        selectedBulb = bulbLiveData.value?.get(pos)
        selectedPostion = pos
    }
    fun getPowerItem(pos:Int){
        selectedPower = powerLiveData.value?.get(pos)
        selectedPostion = pos
    }

    fun getImageResource(bulb : BulbModel): Int{
        when(bulb.bulbcolor){
            1 -> {
                return R.drawable.red_lt_bulb
            }
            2 -> {
                return R.drawable.grn_lt_bulb
            }
            3 -> {
                return R.drawable.blue_lt_bulb
            }
            else -> {
                return R.drawable.grey_lt_bulb
            }
        }
    }

    fun setBulbSections(context: Context,sectionList: ArrayList<BulbModel>) {

        val sectionListType: Type = object : TypeToken<java.util.ArrayList<BulbModel?>?>() {}.type
        val sectionListString: String = Gson().toJson(sectionList, sectionListType)
        val editor: SharedPreferences.Editor =
            context.getSharedPreferences("BULB",0).edit().putString("BULB_ARRAY", sectionListString)
        editor.apply()
    }

    fun getBulbSections(context: Context): ArrayList<BulbModel> {
        var sectionList: ArrayList<BulbModel> = arrayListOf()

        val sSections: String = context.getSharedPreferences("BULB",Context.MODE_PRIVATE).getString("BULB_ARRAY", null)
            ?: return java.util.ArrayList()
        val sectionListType = object : TypeToken<java.util.ArrayList<BulbModel?>?>() {}.type
        try {
            sectionList = Gson().fromJson<Any>(sSections, sectionListType) as ArrayList<BulbModel>
            if (sectionList == null) {
                return java.util.ArrayList()
            }
        } catch (ex: JsonSyntaxException) {
            return java.util.ArrayList()
        } catch (exc: JsonParseException) {
            return java.util.ArrayList()
        }

        return sectionList
    }

    fun setPowerSections(context: Context,sectionList: ArrayList<PowerPlug>) {

        val sectionListType: Type = object : TypeToken<java.util.ArrayList<PowerPlug?>?>() {}.type
        val sectionListString: String = Gson().toJson(sectionList, sectionListType)
        val editor: SharedPreferences.Editor =
            context.getSharedPreferences("POWER",0).edit().putString("Power_ARRAY", sectionListString)
        editor.apply()
    }

    fun getPowerSections(context: Context): ArrayList<PowerPlug> {
        var sectionList: ArrayList<PowerPlug> = arrayListOf()

        val sSections: String = context.getSharedPreferences("POWER",Context.MODE_PRIVATE).getString("Power_ARRAY", null)
            ?: return java.util.ArrayList()
        val sectionListType = object : TypeToken<java.util.ArrayList<PowerPlug?>?>() {}.type
        try {
            sectionList = Gson().fromJson<Any>(sSections, sectionListType) as ArrayList<PowerPlug>
            if (sectionList == null) {
                return java.util.ArrayList()
            }
        } catch (ex: JsonSyntaxException) {
            return java.util.ArrayList()
        } catch (exc: JsonParseException) {
            return java.util.ArrayList()
        }

        return sectionList
    }
 fun setCameraSections(context: Context,sectionList: ArrayList<CameraModel>) {

        val sectionListType: Type = object : TypeToken<java.util.ArrayList<CameraModel?>?>() {}.type
        val sectionListString: String = Gson().toJson(sectionList, sectionListType)
        val editor: SharedPreferences.Editor =
            context.getSharedPreferences("CAMERA",Context.MODE_PRIVATE).edit().putString("CAMERA_ARRAY", sectionListString)
        editor.apply()
    }

    fun getCameraSections(context: Context): ArrayList<CameraModel> {
        var sectionList: ArrayList<CameraModel> = arrayListOf()

        val sSections: String = context.getSharedPreferences("CAMERA",Context.MODE_PRIVATE).getString("CAMERA_ARRAY", null)
            ?: return java.util.ArrayList()
        val sectionListType = object : TypeToken<java.util.ArrayList<CameraModel?>?>() {}.type
        try {
            sectionList = Gson().fromJson<Any>(sSections, sectionListType) as ArrayList<CameraModel>
            if (sectionList == null) {
                return java.util.ArrayList()
            }
        } catch (ex: JsonSyntaxException) {
            return java.util.ArrayList()
        } catch (exc: JsonParseException) {
            return java.util.ArrayList()
        }

        return sectionList
    }


    fun saveMainList(context: Context, collageList: List<String>) {
        val mPrefs = context.getSharedPreferences("CONTROLS", Context.MODE_PRIVATE)
        val prefsEditor = mPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(collageList)
        prefsEditor.putString("controls", json)
        prefsEditor.commit()
    }

    fun getMainList(context: Context): List<String>? {
        var savedCollage: List<String> = ArrayList()
        val mPrefs = context.getSharedPreferences("CONTROLS", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString("controls", "")
        savedCollage = if (json!!.isEmpty()) {
            ArrayList()
        } else {
            val type = object : TypeToken<List<String?>?>() {}.type
            gson.fromJson(json, type)
        }
        return savedCollage
    }

}