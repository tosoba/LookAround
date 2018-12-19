package com.example.there.aroundmenow.places.placetypes.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.databinding.PlaceTypeGroupItemBinding
import com.example.there.aroundmenow.model.UIPlaceTypeGroup

class PlaceTypeGroupsAdapter(
    private val placeTypeGroups: List<UIPlaceTypeGroup>
) : RecyclerView.Adapter<PlaceTypeGroupsAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

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
        holder.binding.group = group
        holder.adapter.placeTypes = group.placeTypes
        Glide.with(holder.binding.root)
            .applyDefaultRequestOptions(RequestOptions().fitCenter())
            .load(group.drawableId)
            .into(holder.groupImageView)
    }

    inner class ViewHolder(val binding: PlaceTypeGroupItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val adapter: PlaceTypesAdapter = PlaceTypesAdapter()

        val groupImageView: ImageView = binding.root.findViewById(R.id.group_image_view)

        init {
            val maxChildItemCount = placeTypeGroups.asSequence().map { it.placeTypes.size }.max()!!
            with(binding.root.findViewById<RecyclerView>(R.id.place_types_recycler_view)) {
                layoutManager = LinearLayoutManager(
                    binding.root.context,
                    RecyclerView.HORIZONTAL,
                    false
                )
                adapter = this@ViewHolder.adapter
                setItemViewCacheSize(maxChildItemCount)
                setRecycledViewPool(viewPool)
            }
        }
    }
}