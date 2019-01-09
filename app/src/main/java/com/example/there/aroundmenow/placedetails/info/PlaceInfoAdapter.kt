package com.example.there.aroundmenow.placedetails.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.model.UIPlace
import com.example.there.aroundmenow.util.ext.placeTypesNames
import kotlinx.android.synthetic.main.place_info_attribute.view.*
import kotlinx.android.synthetic.main.place_info_types_list_item.view.*

class PlaceInfoAdapter(val place: UIPlace) : RecyclerView.Adapter<PlaceInfoAdapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = if (position == 0)
        PLACE_TYPES_LIST_VIEW_TYPE
    else PLACE_ATTRIBUTE_VIEW_TYPE

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = if (viewType == PLACE_ATTRIBUTE_VIEW_TYPE) ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.place_info_attribute, parent, false)
    ) else ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.place_info_types_list_item, parent, false).apply {
            place_info_types_recycler_view.layoutManager =
                    LinearLayoutManager(parent.context, RecyclerView.HORIZONTAL, false)
        }
    )

    override fun getItemCount(): Int = if (place.phoneNumber.isNullOrBlank()) 2 else 3

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> holder.view.place_info_types_recycler_view.adapter = PlaceInfoTypesAdapter(place.placeTypesNames)

            1 -> with(holder.view) {
                place_info_attribute_label_text_view.text = context.resources.getString(R.string.address)
                place_info_attribute_value_text_view.text = place.address
            }

            2 -> with(holder.view) {
                place_info_attribute_label_text_view.text = context.resources.getString(R.string.phone_number)
                place_info_attribute_value_text_view.text = place.phoneNumber
            }
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    companion object {
        private const val PLACE_TYPES_LIST_VIEW_TYPE = 1
        private const val PLACE_ATTRIBUTE_VIEW_TYPE = 2
    }
}