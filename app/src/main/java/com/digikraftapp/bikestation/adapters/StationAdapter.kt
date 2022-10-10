package com.digikraftapp.bikestation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digikraftapp.bikestation.databinding.ListItemStationBinding
import com.digikraftapp.bikestation.model.Feature
import java.util.Collections.emptyList

class StationAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<StationAdapter.ViewHolder>() {


    var stationList: List<Feature> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ListItemStationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data: Feature = stationList[position]

        holder.itemView.setOnClickListener {
            onClickListener.onClick(data)
        }

        holder.bind(data)

    }


    /*  The listview Item count will be dynamic according to the list count.
    *   Returns the total number of items in the data set held by the adapter.
    * */

    override fun getItemCount(): Int = stationList.size

    /**
     * Listview item-related data will be set here.  Adapters should feel free to use their own
     * custom ViewHolder implementations to store data that makes binding view contents easier.
     * Implementations should assume that individual item views will hold strong references to
     * ViewHolder objects and that RecyclerView instances may hold strong references to extra
     * off-screen item views for caching purposes
     */

    inner class ViewHolder(private val binding: ListItemStationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(jobData: Feature) {
            binding.apply {
                tvStationName.text = jobData.properties.label
                tvFreeBikeCount.text = jobData.properties.freeRacks
                tvAllBikeCount.text = jobData.properties.bikeRacks
            }
        }
    }

    /**
     * List view item calls back function.
     * This function needs to be implemented when the adapter configuration.
     */
    class OnClickListener(val clickListener: (station: Feature) -> Unit) {
        fun onClick(station: Feature) = clickListener(station)
    }
}