package com.example.there.lookaround.places.favourites

import android.os.Bundle
import com.example.there.lookaround.R
import com.example.there.lookaround.base.architecture.view.RxFragment
import com.example.there.lookaround.base.architecture.view.ViewDataState
import com.example.there.lookaround.list.simpleplaces.SimplePlacesListEvent
import com.example.there.lookaround.list.simpleplaces.SimplePlacesListFragment
import com.example.there.lookaround.main.LocationUnavailableError
import com.example.there.lookaround.main.MainState
import com.example.there.lookaround.model.UIPlace
import com.example.there.lookaround.model.UISimplePlace
import com.example.there.lookaround.placedetails.PlaceDetailsFragment
import com.example.there.lookaround.util.event.EventTags
import com.example.there.lookaround.util.event.TaggedEvent
import com.example.there.lookaround.util.ext.mainActivity
import com.example.there.lookaround.util.ext.plusAssign
import com.example.there.lookaround.util.ext.valuesOnly
import com.example.there.lookaround.util.lifecycle.EventBusComponent
import com.example.there.lookaround.visualizer.VisualizerFragment
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.withLatestFrom
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class FavouritesFragment :
    RxFragment.Stateful.HostAware.WithLayout<FavouritesState, MainState, Unit, FavouritesActions>(
        R.layout.fragment_favourites,
        HostAwarenessMode.ACTIVITY_ONLY
    ) {

    private val placesListFragment: SimplePlacesListFragment?
        get() = if (isAdded)
            childFragmentManager.findFragmentById(R.id.favourites_list_fragment) as? SimplePlacesListFragment
        else null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle += EventBusComponent(this)

        if (savedInstanceState == null) actions.getFavouritesPlaces()
    }

    //TODO: check if this works with actual location updates - if distances update after location becomes available
    override fun Observable<FavouritesState>.observe() {
        Observable.combineLatest(
            map { it.places }.valuesOnly().distinctUntilChanged(),
            observableActivityState.map { it.userLatLng },
            BiFunction<ViewDataState.Value<List<UIPlace>>, ViewDataState<LatLng, LocationUnavailableError>, Pair<ViewDataState.Value<List<UIPlace>>, ViewDataState<LatLng, LocationUnavailableError>>> { savedPlaces, userLatLngState ->
                Pair(savedPlaces, userLatLngState)
            }
        ).subscribeWithAutoDispose { (savedPlaces, userLatLngState) ->
            if (userLatLngState is ViewDataState.Value) placesListFragment?.onValue(savedPlaces.value.map {
                UISimplePlace.fromUIPlaceWithUserLatLng(it, userLatLngState.value)
            })
            else placesListFragment?.onValue(savedPlaces.value.map { UISimplePlace.fromUIPlace(it) })
        }

        map { it.listEvent }
            .valuesOnly()
            .withLatestFrom(map { it.places }.valuesOnly())
            .subscribeWithAutoDispose { (listEvent, places) ->
                places.value.find { it.name == listEvent.value.place.name }?.let { uiPlace: UIPlace ->
                    when (listEvent.value) {
                        is SimplePlacesListEvent.DetailsRequest -> mainActivity?.showFragment(
                            PlaceDetailsFragment.with(
                                PlaceDetailsFragment.Arguments.PlaceDetails(
                                    uiPlace,
                                    listEvent.value.place
                                )
                            ), true
                        )
                        is SimplePlacesListEvent.VisualizationRequest -> mainActivity?.checkPermissions(onGranted = {
                            mainActivity?.startLocationUpdatesIfNotStartedYet()
                            mainActivity?.showFragment(
                                VisualizerFragment.with(
                                    VisualizerFragment.Arguments.Places(
                                        listOf(listEvent.value.place)
                                    )
                                ), true
                            )
                        })
                    }
                }
                actions.onListEventHandled()
            }
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPlacesListEvent(taggedEvent: TaggedEvent<SimplePlacesListEvent>) {
        if (taggedEvent.tag == EventTags.FromSimpleListToFavourites) actions.onListEvent(taggedEvent.event)
    }
}
