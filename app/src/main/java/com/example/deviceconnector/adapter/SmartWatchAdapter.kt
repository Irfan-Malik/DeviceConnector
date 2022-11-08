package com.example.deviceconnector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deviceconnector.databinding.SmartItemBinding
import com.example.deviceconnector.model.smart_watch.SmartWatchModel

class SmartWatchAdapter(
    private val context: Context,
    private var list: ArrayList<SmartWatchModel>,
    var myCallBack: (SmartWatchModel) -> Unit
) : RecyclerView.Adapter<SmartWatchAdapter.ViewHolder>(){


    inner class ViewHolder(val binding: SmartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewHolder: ViewHolder, position: Int){
            binding.image.setImageResource(list[position].image)
            binding.itemTitle.text = list[position].name
            binding.mainContainer.setOnClickListener {
                myCallBack.invoke(list[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SmartItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(holder,position)
    }

    override fun getItemCount(): Int {
        return  list.size
    }


}