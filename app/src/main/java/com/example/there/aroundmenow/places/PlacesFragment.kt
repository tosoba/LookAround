package com.example.there.aroundmenow.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.ViewObservingFragment
import com.example.there.aroundmenow.databinding.FragmentPlacesBinding
import com.example.there.aroundmenow.places.placetypes.PlaceTypesFragment
import com.example.there.aroundmenow.places.pois.POIsFragment
import com.example.there.aroundmenow.util.view.FragmentViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_places.*


class PlacesFragment : ViewObservingFragment() {

    private val viewPagerAdapter by lazy {
        FragmentViewPagerAdapter(
            manager = childFragmentManager,
            fragments = arrayOf(PlaceTypesFragment(), POIsFragment())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<FragmentPlacesBinding>(
        layoutInflater,
        R.layout.fragment_places,
        container,
        false
    ).apply {
        pagerAdapter = viewPagerAdapter
    }.root

    override fun observeViews() {
        places_bottom_navigation_view.onItemWithIdSelected {
            places_view_pager.currentItem = viewPagerItemIndexes[it]!!
        }
    }

    companion object {
        private val viewPagerItemIndexes = mapOf(
            R.id.bottom_navigation_place_types_item to 0,
            R.id.bottom_navigation_pois_item to 1
        )
    }
}
