package com.example.there.aroundmenow.visualizer

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.FragmentBindingInitializer
import com.example.there.aroundmenow.base.architecture.view.ViewObservingFragment
import com.example.there.aroundmenow.databinding.FragmentVisualizerBinding
import com.example.there.aroundmenow.model.UISimplePlace
import com.example.there.aroundmenow.util.ext.checkItem
import com.example.there.aroundmenow.util.view.viewpager.FragmentViewPagerAdapter
import com.example.there.aroundmenow.visualizer.camera.CameraFragment
import com.example.there.aroundmenow.visualizer.map.MapFragment
import com.example.there.aroundmenow.visualizer.placelist.PlacesListFragment
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_visualizer.*


class VisualizerFragment : ViewObservingFragment(), FragmentBindingInitializer<FragmentVisualizerBinding> {

    private val viewPagerAdapter by lazy {
        FragmentViewPagerAdapter(
            manager = childFragmentManager,
            fragments = arrayOf(CameraFragment(), MapFragment(), PlacesListFragment())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initializeView(R.layout.fragment_visualizer, inflater, container)

    override fun FragmentVisualizerBinding.init() {
        pagerAdapter = viewPagerAdapter
    }

    override fun observeViews() {
        super.observeViews()
        visualizer_bottom_navigation_view.onItemWithIdSelected {
            visualizer_view_pager.currentItem = viewPagerItemIndexes[it]!!
        }

        visualizer_view_pager.onPageSelected {
            visualizer_bottom_navigation_view?.checkItem(it)
        }
    }

    sealed class Arguments : Parcelable {
        @Parcelize
        data class SinglePlace(val place: UISimplePlace) : Arguments()

        @Parcelize
        data class NearbyPlaces(val places: List<UISimplePlace>) : Arguments()
    }

    companion object {
        private val viewPagerItemIndexes = mapOf(
            R.id.bottom_navigation_camera_item to 0,
            R.id.bottom_navigation_map_item to 1,
            R.id.bottom_navigation_places_list_item to 2
        )

        private const val ARG_PLACE = "ARG_PLACE"

        fun with(
            arguments: Arguments
        ): VisualizerFragment = VisualizerFragment().apply {
            this.arguments = Bundle().apply {
                putParcelable(ARG_PLACE, arguments)
            }
        }
    }
}
