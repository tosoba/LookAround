package com.example.there.aroundmenow.places.pois.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.databinding.SimplePlaceItemBinding
import com.example.there.aroundmenow.model.UISimplePlace
import com.example.there.aroundmenow.util.view.recyclerview.SimpleListDiffUtilCallback
import io.reactivex.subjects.PublishSubject

class POIsAdapter : RecyclerView.Adapter<POIsAdapter.ViewHolder>() {

    var pois: List<UISimplePlace> = emptyList()
        set(value) {
            DiffUtil.calculateDiff(POIsAdapter.DiffUtilCallback(field, value))
                .dispatchUpdatesTo(this)
            field = value
        }

    val poiSelected: PublishSubject<UISimplePlace> = PublishSubject.create()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.simple_place_item,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = pois.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.place = pois[position]
    }

    inner class ViewHolder(val binding: SimplePlaceItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { _ ->
                binding.place?.let { poiSelected.onNext(it) }
            }
        }
    }

    class DiffUtilCallback(
        oldPOIs: List<UISimplePlace>,
        newPOIs: List<UISimplePlace>
    ) : SimpleListDiffUtilCallback<UISimplePlace>(oldPOIs, newPOIs) {

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