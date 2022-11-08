package com.example.deviceconnector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deviceconnector.R
import com.example.deviceconnector.databinding.CameraItemLiveBinding
import com.example.deviceconnector.model.PowerPlug

class PowerAdapterLive(
    private val context: Context,
    private var list: ArrayList<PowerPlug>,
    var myCallBack: (Int) -> Unit
) : RecyclerView.Adapter<PowerAdapterLive.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val binding = CameraItemLiveBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(viewHolder,i)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun setData(list:ArrayList<PowerPlug>){
        this.list = list
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: CameraItemLiveBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewHolder: ViewHolder, position: Int){
            binding.title.text = list.get(position).name
            binding.bulb.visibility = View.GONE
            if(list[position].isLive) {
                binding.image.setBackgroundResource(list.get(position).image)
            }
            else{
                binding.image.setBackgroundResource(R.drawable.background_black)
            }

            binding.mainContainer.setOnClickListener {
                myCallBack.invoke(position)
            }

        }
    }



}