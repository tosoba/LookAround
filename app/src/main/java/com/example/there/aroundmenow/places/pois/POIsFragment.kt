package com.example.there.aroundmenow.places.pois

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.example.there.aroundmenow.main.MainState
import com.example.there.aroundmenow.places.pois.recyclerview.POIsAdapter
import com.example.there.aroundmenow.util.ext.withPreviousValue
import io.reactivex.Observable
import io.reactivex.rxkotlin.zipWith
import kotlinx.android.synthetic.main.fragment_pois.*


class POIsFragment : RxFragment.RxHostAware.WithLayout<POIsState, MainState, POIsActions>(
    R.layout.fragment_pois
) {
    private val poisAdapter: POIsAdapter by lazy { POIsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pois_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        pois_recycler_view.adapter = poisAdapter
    }

    override fun Observable<POIsState>.observe() = subscribeWithAutoDispose { state ->
        when (state.pois) {
            is ViewData.Value -> {
                pois_loading_progress_bar?.visibility = View.GONE
                if (state.pois.value.isEmpty()) {
                    no_pois_found_text_view?.visibility = View.VISIBLE
                } else {
                    no_pois_found_text_view?.visibility = View.GONE
                    poisAdapter.pois = state.pois.value
                }
            }
            is ViewData.Error -> {
                pois_loading_progress_bar?.visibility = View.GONE
                Log.e(this@POIsFragment.javaClass.name, "POIs loading error.")
            }
            is ViewData.Loading -> {
                pois_loading_progress_bar?.visibility = View.VISIBLE
                no_pois_found_text_view?.visibility = View.GONE
            }
        }
    }

    override fun Observable<MainState>.observeHost() {
        map { it.userLatLng }
            .withPreviousValue(ViewData.Idle)
            .zipWith(observableStateHolderSharer.observableState)
            .subscribeWithAutoDispose { (lastTwoUserLocations, state) ->
                val (previous, latest) = lastTwoUserLocations
                when {
                    previous is ViewData.Idle && latest is ViewData.Value -> {
                        if (!state.pois.hasValue) actions.findPOIsNearby(latest.value)
                    }
                    previous is ViewData.Value && latest is ViewData.Value -> {
                        // TODO: before running findPOIsNearby() calculate the distance between last and new location
                        // only if greater than some value run method
                    }
                }
            }
    }
}
