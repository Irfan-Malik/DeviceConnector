package com.example.deviceconnector.ui.bulb

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
import com.example.deviceconnector.databinding.CameraLayoutBinding
import com.example.deviceconnector.util.FragmentStack
import com.example.deviceconnector.viewModel.DashBoardViewModel


class BulbFragment : Fragment() {
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
        setData()
    }

    fun setData(){
        binding.addCamer.text = "Add Bulb"
    }

    private val adapter: BulbAdapter by lazy {
        BulbAdapter(requireContext(), arrayListOf()) {
            viewModel.getBulbItem(it)
            changeFragment(BulbStateFragment(), "BulbStateFragment")
        }
    }
    private val adapterLive: BulbAdapterLive by lazy {
        BulbAdapterLive(requireContext(), arrayListOf()) {
            viewModel.getBulbItem(it)
            changeFragment(BulbStateFragment(), "BulbStateFragment")
        }
    }

    private fun setAdapter() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerViewCameras.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter
        binding.recyclerViewCameras.adapter = adapterLive
        var bulb = viewModel.getBulbSections(requireActivity())
        if(bulb != null && bulb.size > 0){
            viewModel.bulbLiveData.value = bulb
        }
        else{
            viewModel.getBulbData()
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
        viewModel.bulbLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
            adapterLive.setData(it)
        }

        viewModel.isDataUpdated.observe(viewLifecycleOwner) {
            adapter.setData(viewModel.bulbLiveData.value!!)
            adapterLive.setData(viewModel.bulbLiveData.value!!)
        }
    }

}