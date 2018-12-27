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
import io.reactivex.rxkotlin.zipWith
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class POIsFragment : RxFragment.HostAware.WithLayout<POIsState, MainState, Unit, POIsActions>(
    R.layout.fragment_pois,
    HostAwarenessMode.ACTIVITY_ONLY
) {
    private val placesListFragment: SimplePlacesListFragment?
        get() = childFragmentManager.findFragmentById(R.id.pois_list_fragment) as? SimplePlacesListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += EventBusComponent(this)
    }

    override fun Observable<POIsState>.observe() = subscribeWithAutoDispose { state ->
        when (state.pois) {
            is ViewDataState.Value -> placesListFragment?.onValue(state.pois.value)
            is ViewDataState.Error -> placesListFragment?.onError()
            is ViewDataState.Loading -> placesListFragment?.onLoading()
        }
    }

    override fun Observable<MainState>.observeActivity() {
        map { it.userLatLng }
            .withPreviousValue(ViewDataState.Idle)
            .zipWith(observableStateHolder.observableState)
            .subscribeWithAutoDispose { (lastTwoUserLocations, state) ->
                val (previous, latest) = lastTwoUserLocations
                when {
                    previous is ViewDataState.Idle && latest is ViewDataState.Value -> {
                        if (!state.pois.hasValue) actions.findPOIsNearby(latest.value)
                    }
                    previous is ViewDataState.Value && latest is ViewDataState.Value -> {
                        // TODO: before running findPOIsNearby() calculate the distance between last and new location
                        // only if greater than some value run method
                    }
                }
            }
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(taggedEvent: TaggedEvent<SimplePlacesListEvent>) {
        if (taggedEvent.tag == EventTags.FromSimpleListToPOIs) {
            when (taggedEvent.event) {
                is SimplePlacesListEvent.DetailsRequest -> mainActivity?.showFragment(
                    PlaceDetailsFragment.with(taggedEvent.event.place),
                    true
                )

                is SimplePlacesListEvent.VisualizationRequest -> mainActivity?.showFragment(
                    VisualizerFragment.with(VisualizerFragment.Arguments.Places(listOf(taggedEvent.event.place))),
                    true
                )
            }
        }
    }
}
