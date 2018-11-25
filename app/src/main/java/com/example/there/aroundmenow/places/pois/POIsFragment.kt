package com.example.there.aroundmenow.places.pois

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.RxFragment
import com.google.android.gms.maps.model.LatLng


class POIsFragment : RxFragment<POIsState, POIsViewModel, POIsActions>(
    POIsViewModel::class.java
) {
    override fun observeState() {
        observableState.subscribe { Log.e("STATE", "STATE") }
            .disposeOnDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pois, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actions.findPOIsNearby(LatLng(51.50354, -0.12768))
    }
}
