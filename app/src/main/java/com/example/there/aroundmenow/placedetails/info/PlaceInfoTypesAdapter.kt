package com.example.there.aroundmenow.placedetails.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.databinding.PlaceInfoTypeItemBinding

class PlaceInfoTypesAdapter(
    private val placeTypesNames: List<String>
) : RecyclerView.Adapter<PlaceInfoTypesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.place_info_type_item,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = placeTypesNames.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.name = placeTypesNames[position]
    }

    class ViewHolder(val binding: PlaceInfoTypeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.placeInfoTypeChip.setChipBackgroundColorResource(R.color.colorPrimary)
        }
    }
}