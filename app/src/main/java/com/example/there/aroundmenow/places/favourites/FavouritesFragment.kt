package com.example.there.aroundmenow.places.favourites

import android.os.Bundle
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.list.simpleplaces.SimplePlacesListEvent
import com.example.there.aroundmenow.list.simpleplaces.SimplePlacesListFragment
import com.example.there.aroundmenow.main.MainState
import com.example.there.aroundmenow.model.UIPlace
import com.example.there.aroundmenow.model.UISimplePlace
import com.example.there.aroundmenow.placedetails.PlaceDetailsFragment
import com.example.there.aroundmenow.util.event.EventTags
import com.example.there.aroundmenow.util.event.TaggedEvent
import com.example.there.aroundmenow.util.ext.mainActivity
import com.example.there.aroundmenow.util.ext.plusAssign
import com.example.there.aroundmenow.util.ext.valuesOnly
import com.example.there.aroundmenow.util.lifecycle.EventBusComponent
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
            BiFunction<ViewDataState.Value<List<UIPlace>>, ViewDataState<LatLng, Nothing>, Pair<ViewDataState.Value<List<UIPlace>>, ViewDataState<LatLng, Nothing>>> { savedPlaces, userLatLngState ->
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
                        is SimplePlacesListEvent.VisualizationRequest -> {
                            //TODO: go to visualizer with saved place only if userLatLng is available
                        }
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
