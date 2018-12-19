package com.example.there.aroundmenow.places.placetypes

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.places.placetypes.recyclerview.PlaceTypeGroupsAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_place_types.*


class PlaceTypesFragment :
    RxFragment.HostUnaware.WithLayout<PlaceTypesState, PlaceTypesActions>(R.layout.fragment_place_types) {

    override fun Observable<PlaceTypesState>.observe() = subscribeWithAutoDispose { state ->
        place_type_groups_recycler_view?.let {
            it.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            it.setHasFixedSize(true)
            it.adapter = PlaceTypeGroupsAdapter(state.placeTypeGroups)
            it.setItemViewCacheSize(state.placeTypeGroups.size)
        }
    }
}
