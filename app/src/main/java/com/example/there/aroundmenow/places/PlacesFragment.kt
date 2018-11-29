package com.example.there.aroundmenow.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.RxDisposer
import com.example.there.aroundmenow.databinding.FragmentPlacesBinding
import com.example.there.aroundmenow.places.placetypes.PlaceTypesFragment
import com.example.there.aroundmenow.places.pois.POIsFragment
import com.example.there.aroundmenow.util.ext.onItemWithIdSelected
import com.example.there.aroundmenow.util.ext.plusAssign
import com.example.there.aroundmenow.util.lifecycle.UiDisposablesComponent
import com.example.there.aroundmenow.util.view.FragmentViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_places.*


class PlacesFragment : Fragment(), RxDisposer {

    private val viewPagerItemIndexes =
        mapOf(R.id.bottom_navigation_place_types_item to 0, R.id.bottom_navigation_pois_item to 1)

    private val viewPagerAdapter by lazy {
        FragmentViewPagerAdapter(
            manager = childFragmentManager,
            fragments = arrayOf(PlaceTypesFragment(), POIsFragment())
        )
    }

    override val uiDisposables = UiDisposablesComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += uiDisposables
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        places_bottom_navigation_view.onItemWithIdSelected {
            places_view_pager.currentItem = viewPagerItemIndexes[it]!!
        }.disposeOnDestroy()
    }
}
