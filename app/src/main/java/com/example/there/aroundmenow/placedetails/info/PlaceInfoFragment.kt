package com.example.there.aroundmenow.placedetails.info


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.placedetails.PlaceDetailsState
import com.example.there.aroundmenow.util.ext.placeTypesNames
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_place_info.*


class PlaceInfoFragment : RxFragment.Stateless.HostAware.WithLayout<Unit, PlaceDetailsState>(
    R.layout.fragment_place_info,
    HostAwarenessMode.PARENT_FRAGMENT_ONLY
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        place_info_types_recycler_view?.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun Observable<PlaceDetailsState>.observeParentFragment() = map { it.place }.subscribeWithAutoDispose {
        if (it is ViewDataState.Value) {
            place_info_types_recycler_view?.adapter = PlaceInfoTypesAdapter(it.value.placeTypesNames)
        }
    }
}
