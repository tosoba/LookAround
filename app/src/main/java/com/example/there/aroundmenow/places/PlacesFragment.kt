package com.example.there.aroundmenow.places

import android.location.Address
import android.widget.Toast
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.databinding.FragmentPlacesBinding
import com.example.there.aroundmenow.main.MainState
import com.example.there.aroundmenow.places.favourites.FavouritesFragment
import com.example.there.aroundmenow.places.placetypes.PlaceTypesFragment
import com.example.there.aroundmenow.places.pois.POIsFragment
import com.example.there.aroundmenow.util.ext.checkItem
import com.example.there.aroundmenow.util.ext.wholeAddressString
import com.example.there.aroundmenow.util.view.viewpager.FragmentViewPagerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_places.*
import kotlinx.android.synthetic.main.reverse_geocoding_result_dialog.view.*
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
            .debounce(1, TimeUnit.SECONDS)
            .subscribeWithAutoDispose { actions.reverseGeocodeLocation() }
    }

    override fun Observable<PlacesState>.observe() = subscribeWithAutoDispose {
        when (it.lastGeocodingResult) {
            is ViewDataState.Value -> onReverseGeocodingResultValue(it.lastGeocodingResult.value.address)
            is ViewDataState.Error -> onReverseGeocodingError()
        }
    }

    private fun onReverseGeocodingResultValue(address: Address) {
        context?.let {
            BottomSheetDialog(it).run {
                setContentView(layoutInflater.inflate(
                    R.layout.reverse_geocoding_result_dialog,
                    null,
                    false
                ).apply {
                    reverse_geocoded_address_text_view?.text = address.wholeAddressString
                })
                show()
            }
        }
    }

    private fun onReverseGeocodingError() {
        context?.let {
            Toast.makeText(it, getString(R.string.unable_to_find_your_address), Toast.LENGTH_LONG).show()
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


