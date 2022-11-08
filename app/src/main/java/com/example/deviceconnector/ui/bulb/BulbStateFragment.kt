package com.example.deviceconnector.ui.bulb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.deviceconnector.R
import com.example.deviceconnector.databinding.CameraStateLayoutBinding
import com.example.deviceconnector.viewModel.DashBoardViewModel

class BulbStateFragment : Fragment() {
    lateinit var binding: CameraStateLayoutBinding
    private val viewModel: DashBoardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CameraStateLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDate()
        clickButtons()
    }

    private fun setDate() {
        binding.title.text = viewModel.selectedBulb?.name
        binding.bulbStates.visibility = View.VISIBLE
        if (viewModel.selectedBulb?.isLive == true) {
            viewModel.selectedBulb?.image?.let {
                binding.imageCamera.setImageResource(viewModel.getImageResource(viewModel.selectedBulb!!))
            }
            binding.playPause.setImageResource(R.drawable.power_pause)
        } else {
            binding.imageCamera.setImageResource(R.drawable.background_black)
            binding.playPause.setImageResource(R.drawable.power_play)
        }

    }

    fun clickButtons() {
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.playPause.setOnClickListener {
            viewModel.bulbLiveData.value?.get(viewModel.selectedPostion)?.isLive =
                !viewModel.bulbLiveData.value?.get(viewModel.selectedPostion)?.isLive!!
            viewModel.getBulbItem(viewModel.selectedPostion)
            setDate()
            viewModel.isDataUpdated.value = true
        }
        binding.next.setOnClickListener {
            var itemsSize = viewModel.bulbLiveData?.value?.size
            if (viewModel.selectedPostion == (itemsSize!! - 1)) {
                viewModel.getBulbItem(0)
            } else {
                viewModel.getBulbItem(viewModel.selectedPostion + 1)
            }
            setDate()
        }
        binding.prev.setOnClickListener {
            if (viewModel.selectedPostion == 0) {
                viewModel.getBulbItem(viewModel.bulbLiveData?.value?.size!! - 1)
            } else {
                viewModel.getBulbItem(viewModel.selectedPostion - 1)
            }
            setDate()
        }

        binding.redLight.setOnClickListener {
            bulbColorUpdate(1)
        }
        binding.greenLight.setOnClickListener {
           bulbColorUpdate(2)
        }
        binding.blueLight.setOnClickListener {
           bulbColorUpdate(3)
        }
        binding.greyLight.setOnClickListener {
           bulbColorUpdate(4)
        }
    }

    fun bulbColorUpdate(state : Int){
        viewModel.bulbLiveData?.value?.get(viewModel.selectedPostion)?.bulbcolor  = state
        viewModel.isDataUpdated.value = true
        setDate()
    }

}