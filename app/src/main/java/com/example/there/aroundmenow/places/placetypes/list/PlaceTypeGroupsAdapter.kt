package com.example.there.aroundmenow.places.placetypes.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.databinding.PlaceTypeGroupItemBinding
import com.example.there.aroundmenow.model.UIPlaceType
import com.example.there.aroundmenow.model.UIPlaceTypeGroup
import io.reactivex.subjects.PublishSubject

class PlaceTypeGroupsAdapter(
    private val placeTypeGroups: List<UIPlaceTypeGroup>
) : RecyclerView.Adapter<PlaceTypeGroupsAdapter.ViewHolder>() {

    private val childrenViewPool = RecyclerView.RecycledViewPool()

    val placeTypeSelected: PublishSubject<UIPlaceType> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.place_type_group_item,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = placeTypeGroups.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = placeTypeGroups[position]
        with(holder) {
            binding.group = group
            adapter.placeTypes = group.placeTypes
        }
    }

    inner class ViewHolder(
        val binding: PlaceTypeGroupItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val adapter: PlaceTypesAdapter = PlaceTypesAdapter()

        init {
            with(binding.root.findViewById<RecyclerView>(R.id.place_types_recycler_view)) {
                layoutManager = LinearLayoutManager(
                    binding.root.context,
                    RecyclerView.HORIZONTAL,
                    false
                )
                adapter = this@ViewHolder.adapter.apply {
                    placeTypeSelected.subscribe(this@PlaceTypeGroupsAdapter.placeTypeSelected)
                }
                setHasFixedSize(true)
                setRecycledViewPool(childrenViewPool)
            }
        }
    }
}