package com.example.there.lookaround.places.pois

import com.example.there.lookaround.base.architecture.view.ViewDataState
import com.example.there.lookaround.main.LocationUnavailableError
import com.example.there.lookaround.util.ext.LastTwoValues
import com.google.android.gms.maps.model.LatLng

typealias PairOfLastTwoLatLngsAndConnectivityState = Pair<LastTwoValues<ViewDataState<LatLng, LocationUnavailableError>>, ViewDataState.Value<Boolean>>

typealias LastTwoLatLngsState = LastTwoValues<ViewDataState<LatLng, LocationUnavailableError>>