package com.example.there.aroundmenow.places.placetypes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.RxFragment


class PlaceTypesFragment : RxFragment<PlaceTypesState, PlaceTypesViewModel, PlaceTypesActions>(
    PlaceTypesViewModel::class.java
) {
    override fun observeState() = Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place_types, container, false)
    }
}
