package com.example.there.lookaround.placedetails

import com.example.there.lookaround.base.architecture.vm.RxViewModel
import javax.inject.Inject

class PlaceDetailsViewModel @Inject constructor() : RxViewModel<PlaceDetailsState>(PlaceDetailsState.INITIAL)