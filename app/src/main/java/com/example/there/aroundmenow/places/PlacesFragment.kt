package com.example.there.aroundmenow.places

import android.util.Log
import android.view.View
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.example.there.aroundmenow.databinding.FragmentPlacesBinding
import com.example.there.aroundmenow.places.placetypes.PlaceTypesFragment
import com.example.there.aroundmenow.places.pois.POIsFragment
import com.example.there.aroundmenow.util.ext.checkItem
import com.example.there.aroundmenow.util.view.FragmentViewPagerAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_places.*


class PlacesFragment : RxFragment.HostUnaware.DataBound<PlacesState, PlacesActions, FragmentPlacesBinding>(
    R.layout.fragment_places
) {
    private val viewPagerAdapter by lazy {
        FragmentViewPagerAdapter(
            manager = childFragmentManager,
            fragments = arrayOf(PlaceTypesFragment(), POIsFragment())
        )
    }

    override fun FragmentPlacesBinding.init() {
        pagerAdapter = viewPagerAdapter
        onReverseGeocodingFabClick = View.OnClickListener {
            actions.reverseGeocodeLocation()
        }
    }

    override fun observeViews() {
        places_bottom_navigation_view.onItemWithIdSelected {
            places_view_pager.currentItem = viewPagerItemIndexes[it]!!
        }

        places_view_pager.onPageSelected {
            places_bottom_navigation_view?.checkItem(it)
        }
    }

    override fun Observable<PlacesState>.observe() = subscribeWithAutoDispose {
        when (it.lastGeocodingResult) {
            is ViewData.Value -> Log.e("ADDR", it.lastGeocodingResult.value.formattedAddress)
            is ViewData.Error -> Log.e(this@PlacesFragment.javaClass.name, "Reverse geocoding error.")
        }
    }

    companion object {
        private val viewPagerItemIndexes = mapOf(
            R.id.bottom_navigation_place_types_item to 0,
            R.id.bottom_navigation_pois_item to 1
        )
    }
}
