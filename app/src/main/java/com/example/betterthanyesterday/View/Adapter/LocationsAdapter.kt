package com.example.betterthanyesterday.View.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.betterthanyesterday.data.RemoteLocation
import com.example.betterthanyesterday.databinding.ItemContainerLocationBinding

class LocationsAdapter (
    private val onLocationClicked:(RemoteLocation)->Unit
) : RecyclerView.Adapter<LocationsAdapter.LocationViewHolder>() {

    private val locations = mutableListOf<RemoteLocation>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<RemoteLocation>) {
        locations.clear()
        locations.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            ItemContainerLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    inner class LocationViewHolder(
        private val binding: ItemContainerLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(remoteLocation: RemoteLocation) {
            with(remoteLocation) {
                val location = "$name, $region, $country"
                binding.textRemoteLocation.text = location
                binding.root.setOnClickListener { onLocationClicked(remoteLocation) }
            }
        }
    }
}