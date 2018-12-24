package com.example.there.aroundmenow.placedetails

import com.example.there.aroundmenow.base.architecture.vm.RxViewModel
import javax.inject.Inject

class PlaceDetailsViewModel @Inject constructor() : RxViewModel<PlaceDetailsState>(PlaceDetailsState.INITIAL)