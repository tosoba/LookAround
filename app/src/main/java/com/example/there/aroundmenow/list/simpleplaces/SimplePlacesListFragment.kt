package com.example.there.aroundmenow.list.simpleplaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.DataListFragment
import com.example.there.aroundmenow.base.architecture.view.ViewObservingFragment
import com.example.there.aroundmenow.model.UISimplePlace
import com.example.there.aroundmenow.util.ext.mainActivity
import com.example.there.aroundmenow.visualizer.VisualizerFragment
import kotlinx.android.synthetic.main.fragment_simple_place_list.*


class SimplePlacesListFragment : ViewObservingFragment(), DataListFragment<List<UISimplePlace>> {

    private val placesAdapter: SimplePlacesAdapter by lazy { SimplePlacesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_simple_place_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        simple_places_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        simple_places_recycler_view.adapter = placesAdapter
    }

    override fun observeViews() {
        placesAdapter.placeSelected.subscribeWithAutoDispose {
            mainActivity?.showFragment(
                VisualizerFragment.with(
                    VisualizerFragment.Arguments.SinglePlace(it)
                ), true
            )
        }
    }

    override fun onValue(value: List<UISimplePlace>) {
        simple_places_loading_progress_bar?.visibility = View.GONE
        if (value.isEmpty()) {
            no_simple_places_found_text_view?.visibility = View.VISIBLE
        } else {
            no_simple_places_found_text_view?.visibility = View.GONE
            placesAdapter.places = value
        }
    }

    override fun onError() {
        simple_places_loading_progress_bar?.visibility = View.GONE
    }

    override fun onLoading() {
        simple_places_loading_progress_bar?.visibility = View.VISIBLE
        no_simple_places_found_text_view?.visibility = View.GONE
    }
}
