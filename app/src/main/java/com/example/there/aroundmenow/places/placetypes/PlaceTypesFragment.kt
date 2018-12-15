package com.example.there.aroundmenow.places.placetypes

import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import io.reactivex.Observable


class PlaceTypesFragment :
    RxFragment.HostUnaware.WithLayout<PlaceTypesState, PlaceTypesActions>(R.layout.fragment_place_types) {

    override fun Observable<PlaceTypesState>.observe() {

    }
}
