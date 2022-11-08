package com.example.deviceconnector.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deviceconnector.R
import com.example.deviceconnector.databinding.CameraItemLiveBinding
import com.example.deviceconnector.model.bulb.BulbModel

class BulbAdapterLive(
    private val context: Context,
    private var list: ArrayList<BulbModel>,
    var myCallBack: (Int) -> Unit
) : RecyclerView.Adapter<BulbAdapterLive.ViewHolder>() {


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
    fun setData(list:ArrayList<BulbModel>){
        this.list = list
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: CameraItemLiveBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewHolder: ViewHolder, position: Int){
            binding.title.text = list.get(position).name
            if(list[position].isLive) {
                binding.image.setBackgroundResource(list.get(position).image)
                binding.bulb.setBackgroundResource(getImageResource(list.get(position)))
                binding.bulb.visibility = View.VISIBLE
            }
            else{
                binding.image.setBackgroundResource(R.drawable.background_black)
                binding.bulb.visibility = View.GONE
            }

            binding.mainContainer.setOnClickListener {
                myCallBack.invoke(position)
            }

        }
    }

    fun getImageResource(bulb : BulbModel): Int{
        when(bulb.bulbcolor){
             1 -> {
                return R.drawable.red_lt_bulb
            }
             2 -> {
                return R.drawable.grn_lt_bulb
            }
             3 -> {
                return R.drawable.blue_lt_bulb
            }
             else -> {
                return R.drawable.grey_lt_bulb
            }
        }
    }


}