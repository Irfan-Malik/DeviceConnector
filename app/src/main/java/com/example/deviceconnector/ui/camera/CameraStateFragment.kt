package com.example.deviceconnector.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.deviceconnector.R
import com.example.deviceconnector.databinding.CameraStateLayoutBinding
import com.example.deviceconnector.viewModel.DashBoardViewModel

class CameraStateFragment : Fragment() {
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
        binding.title.text = viewModel.selectedCamera?.name
        if (viewModel.selectedCamera?.isLive == true) {
            viewModel.selectedCamera?.image?.let { binding.imageCamera.setImageResource(it) }
            binding.playPause.setImageResource(R.drawable.power_pause)
        } else {
            binding.imageCamera.setImageResource(R.drawable.background_black)
            binding.playPause.setImageResource(R.drawable.power_play)
        }

    }

    private fun clickButtons() {
        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.playPause.setOnClickListener {
            viewModel.cameraLiveData.value?.get(viewModel.selectedPostion)?.isLive =
                !viewModel.cameraLiveData.value?.get(viewModel.selectedPostion)?.isLive!!
            viewModel.getCameraItem(viewModel.selectedPostion)
            setDate()
            viewModel.isDataUpdated.value = true
        }
        binding.next.setOnClickListener {
            var itemsSize = viewModel.cameraLiveData?.value?.size
            if (viewModel.selectedPostion == (itemsSize!! - 1)) {
                viewModel.getCameraItem(0)
            } else {
                viewModel.getCameraItem(viewModel.selectedPostion + 1)
            }
            setDate()
        }
        binding.prev.setOnClickListener {
            if (viewModel.selectedPostion == 0) {
                viewModel.getCameraItem(viewModel.cameraLiveData?.value?.size!! - 1)
            } else {
                viewModel.getCameraItem(viewModel.selectedPostion - 1)
            }
            setDate()
        }
    }


}