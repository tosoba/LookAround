package com.example.there.aroundmenow.places.placetypes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.ViewObservingFragment
import com.example.there.aroundmenow.places.placetypes.list.PlaceTypeGroupsAdapter
import com.example.there.aroundmenow.places.placetypes.list.placeTypeGroups
import com.example.there.aroundmenow.util.ext.initDefault
import com.example.there.aroundmenow.util.ext.mainActivity
import com.example.there.aroundmenow.visualizer.VisualizerFragment
import kotlinx.android.synthetic.main.fragment_place_types.*


class PlaceTypesFragment : ViewObservingFragment() {

    private val groupsAdapterVertical: PlaceTypeGroupsAdapter by lazy {
        PlaceTypeGroupsAdapter(placeTypeGroups)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_place_types, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        place_type_groups_recycler_view?.initDefault(groupsAdapterVertical)
    }

    override fun observeViews() = groupsAdapterVertical.placeTypeSelected.subscribeWithAutoDispose {
        mainActivity?.checkPermissionsAndThen {
            mainActivity?.showFragment(
                VisualizerFragment.with(VisualizerFragment.Arguments.PlaceType(it)), true
            )
        }
    }

}
