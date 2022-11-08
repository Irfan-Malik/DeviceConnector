package com.example.deviceconnector

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.deviceconnector.databinding.ActivityMainBinding
import com.example.deviceconnector.ui.HomeFragment
import com.example.deviceconnector.util.FragmentStack
import com.example.deviceconnector.viewModel.DashBoardViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    val viewModel : DashBoardViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FragmentStack.addFragmentToContainer(R.id.container,
            supportFragmentManager,HomeFragment(),"HomeFragment")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(supportFragmentManager.backStackEntryCount == 0){
            finish()
        }
    }

    override fun onStop() {
        saveData()
        super.onStop()
    }

    private fun saveData(){
        viewModel.bulbLiveData.value?.let { viewModel.setBulbSections(this, it) }
        viewModel.cameraLiveData.value?.let { viewModel.setCameraSections(this, it) }
        viewModel.enabledWidget.value?.let { viewModel.saveMainList(this,it) }
        viewModel.powerLiveData.value?.let { viewModel.setPowerSections(this,it) }
    }
    object LocationAddress{
        var country :String = ""
        var state :String = ""
        var city :String = ""
        var countryCode :String = ""
    }

}