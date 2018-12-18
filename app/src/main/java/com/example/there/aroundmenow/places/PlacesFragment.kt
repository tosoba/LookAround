package com.example.there.aroundmenow.places

import android.util.Log
import android.view.View
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.example.there.aroundmenow.databinding.FragmentPlacesBinding
import com.example.there.aroundmenow.main.MainActivity
import com.example.there.aroundmenow.main.MainState
import com.example.there.aroundmenow.places.placetypes.PlaceTypesFragment
import com.example.there.aroundmenow.places.pois.POIsFragment
import com.example.there.aroundmenow.util.view.FragmentViewPagerAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_places.*


class PlacesFragment :
    RxFragment.RxHostAware.DataBound<PlacesState, MainState, PlacesActions, FragmentPlacesBinding>(R.layout.fragment_places) {

    private val viewPagerAdapter by lazy {
        FragmentViewPagerAdapter(
            manager = childFragmentManager,
            fragments = arrayOf(PlaceTypesFragment(), POIsFragment())
        )
    }

    override fun FragmentPlacesBinding.init() {
        pagerAdapter = viewPagerAdapter
        onReverseGeocodingFabClick = View.OnClickListener {
            actions.reverseGeocodeLocation(MainActivity.testUserLatLng)
        }
    }

    override fun observeViews() {
        places_bottom_navigation_view.onItemWithIdSelected {
            places_view_pager.currentItem = viewPagerItemIndexes[it]!!
        }
    }

    override fun Observable<PlacesState>.observe() = subscribeWithAutoDispose {
        when (it.lastGeocodingResult) {
            is ViewData.Value -> Log.e("ADDR", it.lastGeocodingResult.value.address)
            is ViewData.Error -> Log.e(this@PlacesFragment.javaClass.name, "Reverse geocoding error.")
        }
    }

    override fun Observable<MainState>.observeHost() {
    }

    companion object {
        private val viewPagerItemIndexes = mapOf(
            R.id.bottom_navigation_place_types_item to 0,
            R.id.bottom_navigation_pois_item to 1
        )
    }
}
