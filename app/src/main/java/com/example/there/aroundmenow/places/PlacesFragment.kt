package com.example.there.aroundmenow.places

import android.util.Log
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.databinding.FragmentPlacesBinding
import com.example.there.aroundmenow.main.MainState
import com.example.there.aroundmenow.places.favourites.FavouritesFragment
import com.example.there.aroundmenow.places.placetypes.PlaceTypesFragment
import com.example.there.aroundmenow.places.pois.POIsFragment
import com.example.there.aroundmenow.util.ext.checkItem
import com.example.there.aroundmenow.util.view.viewpager.FragmentViewPagerAdapter
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_places.*
import java.util.concurrent.TimeUnit


class PlacesFragment :
    RxFragment.Stateful.HostAware.DataBound<PlacesState, MainState, Unit, PlacesActions, FragmentPlacesBinding>(
        R.layout.fragment_places,
        HostAwarenessMode.ACTIVITY_ONLY
    ) {

    private val viewPagerAdapter by lazy {
        FragmentViewPagerAdapter(
            manager = childFragmentManager,
            fragments = arrayOf(PlaceTypesFragment(), POIsFragment(), FavouritesFragment())
        )
    }

    override fun FragmentPlacesBinding.init() {
        pagerAdapter = viewPagerAdapter
    }

    override fun observeViews() {
        places_bottom_navigation_view.onItemWithIdSelected {
            places_view_pager.currentItem = viewPagerItemIndexes[it]!!
        }

        places_view_pager.onPageSelected {
            places_bottom_navigation_view?.checkItem(it)
        }

        reverse_geocoding_fab.clicks()
            .debounce(3, TimeUnit.SECONDS)
            .subscribeWithAutoDispose { actions.reverseGeocodeLocation() }
    }

    override fun Observable<PlacesState>.observe() = subscribeWithAutoDispose {
        when (it.lastGeocodingResult) {
            is ViewDataState.Value -> Log.e(
                "ADDR",
                it.lastGeocodingResult.value.address.getAddressLine(0) ?: "null addr"
            )
            is ViewDataState.Error -> Log.e("ADDR", "Reverse geocoding error.")
            is ViewDataState.Loading -> Log.e("ADDR", "loading")
        }
    }


    companion object {
        private val viewPagerItemIndexes = mapOf(
            R.id.bottom_navigation_place_types_item to 0,
            R.id.bottom_navigation_pois_item to 1,
            R.id.bottom_navigation_favourites_item to 2
        )
    }
}


