package com.example.there.aroundmenow.places.pois

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.example.there.aroundmenow.main.MainActivity
import com.example.there.aroundmenow.model.UISimplePOI
import com.example.there.aroundmenow.places.pois.recyclerview.POIsAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_pois.*


class POIsFragment : RxFragment.HostUnaware.WithLayout<POIsState, POIsActions>(R.layout.fragment_pois) {

    private val poisAdapter: POIsAdapter by lazy { POIsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            actions.findPOIsNearby(MainActivity.testUserLatLng)
        }
    }

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
                    poisAdapter.pois = state.pois.value.map {
                        UISimplePOI.fromDomainWithUserLatLng(
                            it,
                            MainActivity.testUserLatLng
                        )
                    }
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
}
