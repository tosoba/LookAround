package com.example.there.aroundmenow.placedetails

import android.os.Bundle
import android.util.Log
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.model.UISimplePlace
import io.reactivex.Observable


class PlaceDetailsFragment : RxFragment.HostUnaware.WithLayout<PlaceDetailsState, PlaceDetailsActions>(
    R.layout.fragment_place_details
) {
    private val simplePlace: UISimplePlace by lazy {
        arguments!!.getParcelable<UISimplePlace>(ARG_PLACE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) actions.findPlaceDetails(simplePlace)
    }

    override fun Observable<PlaceDetailsState>.observe() = map { it.place }.subscribeWithAutoDispose {
        when (it) {
            is ViewDataState.Value -> Log.e("PLACE", it.value.name.toString())
        }
    }

    companion object {
        private const val ARG_PLACE = "ARG_PLACE"

        fun with(place: UISimplePlace): PlaceDetailsFragment = PlaceDetailsFragment().apply {
            arguments = Bundle().apply { putParcelable(ARG_PLACE, place) }
        }
    }
}
