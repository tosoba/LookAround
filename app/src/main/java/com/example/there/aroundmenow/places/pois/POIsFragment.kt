package com.example.there.aroundmenow.places.pois

import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.example.there.aroundmenow.list.simpleplaces.SimplePlacesListFragment
import com.example.there.aroundmenow.main.MainState
import com.example.there.aroundmenow.util.ext.withPreviousValue
import io.reactivex.Observable
import io.reactivex.rxkotlin.zipWith


class POIsFragment : RxFragment.HostAware.WithLayout<POIsState, MainState, POIsActions>(
    R.layout.fragment_pois
) {
    private val placesListFragment: SimplePlacesListFragment?
        get() = childFragmentManager.findFragmentById(R.id.pois_list_fragment) as? SimplePlacesListFragment

    override fun Observable<POIsState>.observe() = subscribeWithAutoDispose { state ->
        when (state.pois) {
            is ViewData.Value -> placesListFragment?.onValue(state.pois.value)
            is ViewData.Error -> placesListFragment?.onError()
            is ViewData.Loading -> placesListFragment?.onLoading()
        }
    }

    override fun Observable<MainState>.observeHost() {
        map { it.userLatLng }
            .withPreviousValue(ViewData.Idle)
            .zipWith(observableStateHolderSharer.observableState)
            .subscribeWithAutoDispose { (lastTwoUserLocations, state) ->
                val (previous, latest) = lastTwoUserLocations
                when {
                    previous is ViewData.Idle && latest is ViewData.Value -> {
                        if (!state.pois.hasValue) actions.findPOIsNearby(latest.value)
                    }
                    previous is ViewData.Value && latest is ViewData.Value -> {
                        // TODO: before running findPOIsNearby() calculate the distance between last and new location
                        // only if greater than some value run method
                    }
                }
            }
    }
}
