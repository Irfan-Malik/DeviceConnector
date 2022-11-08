package com.example.deviceconnector.ui.power

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.deviceconnector.R
import com.example.deviceconnector.adapter.BulbAdapter
import com.example.deviceconnector.adapter.BulbAdapterLive
import com.example.deviceconnector.adapter.PowerAdapter
import com.example.deviceconnector.adapter.PowerAdapterLive
import com.example.deviceconnector.databinding.CameraLayoutBinding
import com.example.deviceconnector.databinding.PowerPlugLayoutBinding
import com.example.deviceconnector.ui.bulb.BulbStateFragment
import com.example.deviceconnector.util.FragmentStack
import com.example.deviceconnector.viewModel.DashBoardViewModel

class PowerPlugFragment : Fragment() {

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
        setData()

    }

    private fun setData(){
        setAdapter()
    }

    private val adapter: PowerAdapter by lazy {
        PowerAdapter(requireContext(), arrayListOf()) {
            viewModel.getPowerItem(it)
            changeFragment(PowerStateFragment(), "PowerStateFragment")
        }
    }
    private val adapterLive: PowerAdapterLive by lazy {
        PowerAdapterLive(requireContext(), arrayListOf()) {
            viewModel.getPowerItem(it)
            changeFragment(PowerStateFragment(), "PowerStateFragment")
        }
    }

    private fun setAdapter() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewCameras.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter
        binding.recyclerViewCameras.adapter = adapterLive
        var power = viewModel.getPowerSections(requireActivity())
        if(power != null && power.size > 0){
            viewModel.powerLiveData.value = power
        }
        else{
            viewModel.getPowerData()
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
        viewModel.powerLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
            adapterLive.setData(it)
        }

        viewModel.isDataUpdated.observe(viewLifecycleOwner) {
            adapter.setData(viewModel.powerLiveData.value!!)
            adapterLive.setData(viewModel.powerLiveData.value!!)
        }
    }

}