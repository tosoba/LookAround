package com.example.there.aroundmenow.places.placetypes.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.databinding.PlaceTypeItemBinding
import com.example.there.aroundmenow.model.UIPlaceType
import com.example.there.aroundmenow.util.view.recyclerview.SimpleListDiffUtilCallback

class PlaceTypesAdapter : RecyclerView.Adapter<PlaceTypesAdapter.ViewHolder>() {

    var placeTypes: List<UIPlaceType> = emptyList()
        set(value) {
            DiffUtil.calculateDiff(PlaceTypesAdapter.DiffUtilCallback(field, value)).dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.place_type_item,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = placeTypes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.placeType = placeTypes[position]
    }

    class ViewHolder(val binding: PlaceTypeItemBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffUtilCallback(
        oldPlaceTypes: List<UIPlaceType>,
        newPlaceTypes: List<UIPlaceType>
    ) : SimpleListDiffUtilCallback<UIPlaceType>(oldPlaceTypes, newPlaceTypes) {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val (oldItem, newItem) = getItemPair(oldItemPosition, newItemPosition)
            return oldItem.label == newItem.label
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val (oldItem, newItem) = getItemPair(oldItemPosition, newItemPosition)
            return oldItem == newItem
        }

    }
}