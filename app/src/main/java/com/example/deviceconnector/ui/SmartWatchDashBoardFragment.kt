package com.example.deviceconnector.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.deviceconnector.adapter.SmartWatchAdapter
import com.example.deviceconnector.databinding.SmartWatchLayoutBinding
import com.example.deviceconnector.viewModel.DashBoardViewModel

class SmartWatchDashBoardFragment : Fragment() {

    lateinit var binding: SmartWatchLayoutBinding
    private val viewModel: DashBoardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SmartWatchLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private val adapter: SmartWatchAdapter by lazy {
        SmartWatchAdapter(requireContext(), viewModel.getSmartWatchData()) {
            Toast.makeText(requireActivity(), "Connecting to ${it.name}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setData(){
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = adapter

        binding.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}