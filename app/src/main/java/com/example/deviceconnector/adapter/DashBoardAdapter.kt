package com.example.deviceconnector.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deviceconnector.databinding.ItemWidgetsBulbBinding
import com.example.deviceconnector.model.IOT

class DashBoardAdapter(
    private val context: Context,
    private var list: List<IOT>
) : RecyclerView.Adapter<DashBoardAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val binding = ItemWidgetsBulbBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(viewHolder,i)
        Log.e("Item ","Value Title = " + list[i].title)
    }

    override fun getItemCount(): Int {
        return 10//list.size
    }

    fun setData(list:List<IOT>){
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemWidgetsBulbBinding) : RecyclerView.ViewHolder(binding.root) {



        fun bind(viewHolder: ViewHolder, position: Int){
        }
    }


}