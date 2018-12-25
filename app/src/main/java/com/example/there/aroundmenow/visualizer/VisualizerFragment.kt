package com.example.there.aroundmenow.visualizer

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.databinding.FragmentVisualizerBinding
import com.example.there.aroundmenow.main.MainState
import com.example.there.aroundmenow.model.UIPlaceType
import com.example.there.aroundmenow.model.UISimplePlace
import com.example.there.aroundmenow.util.ext.checkItem
import com.example.there.aroundmenow.util.view.viewpager.FragmentViewPagerAdapter
import com.example.there.aroundmenow.visualizer.camera.CameraFragment
import com.example.there.aroundmenow.visualizer.map.MapFragment
import com.example.there.aroundmenow.visualizer.placelist.PlacesListFragment
import io.reactivex.Observable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_visualizer.*


class VisualizerFragment :
    RxFragment.HostAware.DataBound<VisualizerState, MainState, VisualizerActions, FragmentVisualizerBinding>(
        R.layout.fragment_visualizer
    ) {

    private val viewPagerAdapter by lazy {
        FragmentViewPagerAdapter(
            manager = childFragmentManager,
            fragments = arrayOf(CameraFragment(), MapFragment(), PlacesListFragment())
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) initFromArguments()
    }

    override fun Observable<VisualizerState>.observe() = map { it.places }.subscribeWithAutoDispose {
        when (it) {
            is ViewDataState.Value -> Log.e("PLACES", "places updated")
            is ViewDataState.Error -> Log.e("PLACES ERR", "Error")
        }
    }

    override fun Observable<MainState>.observeHost() = map { it.userLatLng }.subscribeWithAutoDispose {
        //TODO: send event to CameraFragment to update user location
    }

    override fun FragmentVisualizerBinding.init() {
        pagerAdapter = viewPagerAdapter
    }

    override fun observeViews() {
        visualizer_bottom_navigation_view.onItemWithIdSelected {
            visualizer_view_pager.currentItem = viewPagerItemIndexes[it]!!
        }

        visualizer_view_pager.onPageSelected {
            visualizer_bottom_navigation_view?.checkItem(it)
        }
    }

    private fun initFromArguments() {
        val args = arguments!!.getParcelable<Arguments>(ARGUMENTS_KEY)
        when (args) {
            is Arguments.Places -> actions.setPlaces(args.places)
            is Arguments.PlaceType -> actions.findNearbyPlacesOfType(args.placeType)
        }
    }

    sealed class Arguments : Parcelable {
        @Parcelize
        data class Places(val places: List<UISimplePlace>) : Arguments()

        @Parcelize
        data class PlaceType(val placeType: UIPlaceType) : Arguments()
    }

    companion object {
        private val viewPagerItemIndexes = mapOf(
            R.id.bottom_navigation_camera_item to 0,
            R.id.bottom_navigation_map_item to 1,
            R.id.bottom_navigation_places_list_item to 2
        )

        private const val ARGUMENTS_KEY = "ARGUMENTS_KEY"

        fun with(
            arguments: Arguments
        ): VisualizerFragment = VisualizerFragment().apply {
            this.arguments = Bundle().apply {
                putParcelable(ARGUMENTS_KEY, arguments)
            }
        }
    }
}
