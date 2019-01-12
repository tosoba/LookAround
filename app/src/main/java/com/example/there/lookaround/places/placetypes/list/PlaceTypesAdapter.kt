package com.example.there.lookaround.places.placetypes.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.there.lookaround.R
import com.example.there.lookaround.databinding.PlaceTypeItemBinding
import com.example.there.lookaround.model.UIPlaceType
import com.example.there.lookaround.util.view.recyclerview.SimpleListDiffUtilCallback
import io.reactivex.subjects.PublishSubject

class PlaceTypesAdapter : RecyclerView.Adapter<PlaceTypesAdapter.ViewHolder>() {

    val placeTypeSelected: PublishSubject<UIPlaceType> = PublishSubject.create()

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

    inner class ViewHolder(
        val binding: PlaceTypeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.placeTypeImageButton.setOnClickListener { _ ->
                binding.placeType?.let { placeTypeSelected.onNext(it) }
            }
        }
    }

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