package com.example.deviceconnector.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.deviceconnector.R
import com.example.deviceconnector.adapter.CameraAdapter
import com.example.deviceconnector.adapter.CameraAdapterLive
import com.example.deviceconnector.databinding.CameraLayoutBinding
import com.example.deviceconnector.util.FragmentStack
import com.example.deviceconnector.viewModel.DashBoardViewModel


class CameraFragment : Fragment() {
    lateinit var binding: CameraLayoutBinding
    private val viewModel: DashBoardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CameraLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private val adapter: CameraAdapter by lazy {
        CameraAdapter(requireContext(), arrayListOf()) {
            viewModel.getCameraItem(it)
            changeFragment(CameraStateFragment(), "CameraStateFragment")
        }
    }
    private val adapterLive: CameraAdapterLive by lazy {
        CameraAdapterLive(requireContext(), arrayListOf()) {
            viewModel.getCameraItem(it)
            changeFragment(CameraStateFragment(), "CameraStateFragment")
        }
    }

    private fun setAdapter() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewCameras.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter
        binding.recyclerViewCameras.adapter = adapterLive
//        viewModel.getCameraData()
        var camera = viewModel.getCameraSections(requireActivity())
        if(camera != null && camera.size > 0){
            viewModel.cameraLiveData.value = camera
        }
        else{
            viewModel.getCameraData()
        }
        observers()
    }

    private fun changeFragment(fragment: Fragment, tag: String) {
        FragmentStack.addFragmentToContainer(
            R.id.container,
            requireActivity().supportFragmentManager, fragment, tag
        )
    }

    private fun observers() {
        viewModel.cameraLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
            adapterLive.setData(it)
        }

        viewModel.isDataUpdated.observe(viewLifecycleOwner) {
            adapter.setData(viewModel.cameraLiveData.value!!)
            adapterLive.setData(viewModel.cameraLiveData.value!!)
        }
    }

}