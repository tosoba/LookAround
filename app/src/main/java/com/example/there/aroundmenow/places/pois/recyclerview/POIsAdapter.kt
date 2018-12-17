package com.example.there.aroundmenow.places.pois.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.databinding.SimplePoiItemBinding
import com.example.there.aroundmenow.model.UISimplePOI
import com.example.there.aroundmenow.util.view.recyclerview.SimpleListDiffUtilCallback

class POIsAdapter : RecyclerView.Adapter<POIsAdapter.ViewHolder>() {

    var pois: List<UISimplePOI> = emptyList()
        set(value) {
            DiffUtil.calculateDiff(POIsAdapter.DiffUtilCallback(field, value)).dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.simple_poi_item,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = pois.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.poi = pois[position]
    }

    class ViewHolder(val binding: SimplePoiItemBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffUtilCallback(
        oldPOIs: List<UISimplePOI>,
        newPOIs: List<UISimplePOI>
    ) : SimpleListDiffUtilCallback<UISimplePOI>(oldPOIs, newPOIs) {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val (oldItem, newItem) = getItemPair(oldItemPosition, newItemPosition)
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val (oldItem, newItem) = getItemPair(oldItemPosition, newItemPosition)
            return oldItem.formattedDistanceFromUser == newItem.formattedDistanceFromUser
        }
    }
}