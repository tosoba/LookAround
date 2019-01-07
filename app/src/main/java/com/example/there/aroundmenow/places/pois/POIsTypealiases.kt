package com.example.there.aroundmenow.places.pois

import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.util.ext.LastTwoValues
import com.google.android.gms.maps.model.LatLng

typealias PairOfLastTwoLatLngsAndConnectivityState = Pair<LastTwoValues<ViewDataState<LatLng, Nothing>>, ViewDataState.Value<Boolean>>

typealias LastTwoLatLngsState = LastTwoValues<ViewDataState<LatLng, Nothing>>