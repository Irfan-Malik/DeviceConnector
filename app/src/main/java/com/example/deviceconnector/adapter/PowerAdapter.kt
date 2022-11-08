package com.example.deviceconnector.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deviceconnector.databinding.CameraItemBinding
import com.example.deviceconnector.model.PowerPlug

class PowerAdapter(
    private val context: Context,
    private var list: ArrayList<PowerPlug>,
    var myCallBack: (Int) -> Unit
) : RecyclerView.Adapter<PowerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val binding = CameraItemBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(viewHolder,i)
        Log.e("Item ","Value Title = " + list[i].name)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list:ArrayList<PowerPlug>){
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: CameraItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(viewHolder: ViewHolder, position: Int){
            binding.title.text = list.get(position).name
            binding.mainContainer.setOnClickListener {
                myCallBack.invoke(position)
            }
        }
    }


}