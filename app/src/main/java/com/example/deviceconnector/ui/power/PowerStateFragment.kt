package com.example.deviceconnector.ui.power

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.deviceconnector.R
import com.example.deviceconnector.databinding.CameraStateLayoutBinding
import com.example.deviceconnector.viewModel.DashBoardViewModel

class PowerStateFragment : Fragment() {
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
        binding.title.text = viewModel.selectedPower?.name
//        binding.bulbStates.visibility = View.VISIBLE
        if (viewModel.selectedPower?.isLive == true) {
            viewModel.selectedPower?.image?.let {
                binding.imageCamera.setImageResource(viewModel.selectedPower?.image!!)
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
            viewModel.powerLiveData.value?.get(viewModel.selectedPostion)?.isLive =
                !viewModel.powerLiveData.value?.get(viewModel.selectedPostion)?.isLive!!
            viewModel.getPowerItem(viewModel.selectedPostion)
            setDate()
            viewModel.isDataUpdated.value = true
        }
        binding.next.setOnClickListener {
            var itemsSize = viewModel.powerLiveData?.value?.size
            if (viewModel.selectedPostion == (itemsSize!! - 1)) {
                viewModel.getPowerItem(0)
            } else {
                viewModel.getPowerItem(viewModel.selectedPostion + 1)
            }
            setDate()
        }
        binding.prev.setOnClickListener {
            if (viewModel.selectedPostion == 0) {
                viewModel.getPowerItem(viewModel.powerLiveData?.value?.size!! - 1)
            } else {
                viewModel.getPowerItem(viewModel.selectedPostion - 1)
            }
            setDate()
        }


    }

}