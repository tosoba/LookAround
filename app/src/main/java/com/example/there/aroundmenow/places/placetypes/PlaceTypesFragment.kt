package com.example.there.aroundmenow.places.placetypes

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.ViewObservingFragment
import com.example.there.aroundmenow.places.placetypes.list.PlaceTypeGroupsAdapter
import com.example.there.aroundmenow.util.ext.mainActivity
import com.example.there.aroundmenow.visualizer.VisualizerFragment
import kotlinx.android.synthetic.main.fragment_place_types.*


class PlaceTypesFragment : ViewObservingFragment() {

    private val placeTypeGroupsAdapter: PlaceTypeGroupsAdapter by lazy {
        PlaceTypeGroupsAdapter(placeTypeGroups)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_place_types, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(place_type_groups_recycler_view) {
            layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            else GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = placeTypeGroupsAdapter
            setHasFixedSize(true)
        }
    }

    override fun observeViews() {
        placeTypeGroupsAdapter.placeTypeSelected.subscribeWithAutoDispose {
            mainActivity?.checkPermissionsAndThen {
                mainActivity?.showFragment(
                    VisualizerFragment.with(VisualizerFragment.Arguments.PlaceType(it)),
                    true
                )
            }
        }
    }
}
