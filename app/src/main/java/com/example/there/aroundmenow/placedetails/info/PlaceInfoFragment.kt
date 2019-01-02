package com.example.there.aroundmenow.placedetails.info


import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.placedetails.PlaceDetailsState


class PlaceInfoFragment : RxFragment.Stateless.HostAware.WithLayout<Unit, PlaceDetailsState>(
    R.layout.fragment_place_info,
    HostAwarenessMode.PARENT_FRAGMENT_ONLY
) {

}
