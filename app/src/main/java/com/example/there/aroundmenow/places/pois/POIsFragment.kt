package com.example.there.aroundmenow.places.pois

import android.os.Bundle
import android.util.Log
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable


class POIsFragment : RxFragment.HostUnaware.WithLayout<POIsState, POIsActions>(R.layout.fragment_pois) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            actions.findPOIsNearby(LatLng(51.50354, -0.12768))
        }
    }

    override fun Observable<POIsState>.observe() = subscribeWithAutoDispose {
        when (it.pois) {
            is ViewData.Value -> Log.e("VALUE", "value")
            is ViewData.Error -> Log.e("ERROR", "error")
            is ViewData.Loading -> Log.e("LOADING", "loading")
        }
    }
}
