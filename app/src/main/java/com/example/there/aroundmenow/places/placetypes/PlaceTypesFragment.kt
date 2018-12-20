package com.example.there.aroundmenow.places.placetypes

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.places.placetypes.recyclerview.PlaceTypeGroupsAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_place_types.*


class PlaceTypesFragment : RxFragment.HostUnaware.WithLayout<PlaceTypesState, PlaceTypesActions>(
    R.layout.fragment_place_types
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(place_type_groups_recycler_view) {
            layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            } else {
                GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            }
            adapter = PlaceTypeGroupsAdapter(PlaceTypesState.Constants.placeTypeGroups)
            setHasFixedSize(true)
        }
    }

    override fun Observable<PlaceTypesState>.observe() {

    }
}
