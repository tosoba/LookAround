package com.example.there.aroundmenow.places.pois

import android.os.Bundle
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.list.simpleplaces.SimplePlacesListEvent
import com.example.there.aroundmenow.list.simpleplaces.SimplePlacesListFragment
import com.example.there.aroundmenow.main.MainState
import com.example.there.aroundmenow.placedetails.PlaceDetailsFragment
import com.example.there.aroundmenow.util.event.EventTags
import com.example.there.aroundmenow.util.event.TaggedEvent
import com.example.there.aroundmenow.util.ext.mainActivity
import com.example.there.aroundmenow.util.ext.plusAssign
import com.example.there.aroundmenow.util.ext.withPreviousValue
import com.example.there.aroundmenow.util.lifecycle.EventBusComponent
import com.example.there.aroundmenow.visualizer.VisualizerFragment
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.withLatestFrom
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class POIsFragment : RxFragment.Stateful.HostAware.WithLayout<POIsState, MainState, Unit, POIsActions>(
    R.layout.fragment_pois,
    HostAwarenessMode.ACTIVITY_ONLY
) {
    private val placesListFragment: SimplePlacesListFragment?
        get() = if (isAdded)
            childFragmentManager.findFragmentById(R.id.pois_list_fragment) as? SimplePlacesListFragment
        else null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += EventBusComponent(this)
    }

    override fun Observable<POIsState>.observe() = subscribeWithAutoDispose { state ->
        when (state.pois) {
            is ViewDataState.Value -> placesListFragment?.onValue(state.pois.value)
            is ViewDataState.Error -> placesListFragment?.onError(getString(R.string.loading_pois_failed))
            is ViewDataState.Loading -> placesListFragment?.onLoading()
        }
    }

    override fun Observable<MainState>.observeActivity() = Observable.combineLatest(
        map { it.userLatLng }.withPreviousValue(ViewDataState.Idle),
        map { it.connectedToInternet }.filter { it.hasValue }.map { it as ViewDataState.Value },
        BiFunction<LastTwoLatLngsState, ViewDataState.Value<Boolean>, PairOfLastTwoLatLngsAndConnectivityState> { latLngs, connected ->
            Pair(latLngs, connected)
        }
    ).withLatestFrom(observableStateHolder.observableState).subscribeWithAutoDispose { (mainState, state) ->
        val (lastTwoUserLocations, connected) = mainState
        val (previous, latest) = lastTwoUserLocations
        if (state.pois.hasValue) {
            // TODO: before running findPOIsNearby() calculate the distance between last and new location
            // only if greater than some value run method
        } else {
            if (connected.value) {
                if (latest is ViewDataState.Value) {
                    actions.findPOIsNearby(latest.value)
                } else {
                    // TODO: onNoLocation()
                }
            } else onNotConnected()

        }
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPlacesListEvent(taggedEvent: TaggedEvent<SimplePlacesListEvent>) {
        if (taggedEvent.tag == EventTags.FromSimpleListToPOIs) {
            when (taggedEvent.event) {
                is SimplePlacesListEvent.DetailsRequest -> mainActivity?.showFragment(
                    PlaceDetailsFragment.with(PlaceDetailsFragment.Arguments.SimplePlace(taggedEvent.event.place)),
                    true
                )

                is SimplePlacesListEvent.VisualizationRequest -> mainActivity?.checkPermissions(onGranted = {
                    mainActivity?.showFragment(
                        VisualizerFragment.with(VisualizerFragment.Arguments.Places(listOf(taggedEvent.event.place))),
                        true
                    )
                })
            }
        }
    }

    private fun onNotConnected() {
        placesListFragment?.onError(getString(R.string.unable_to_load_pois_no_internet_connection))
    }
}
