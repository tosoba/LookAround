package com.example.there.aroundmenow.places.placetypes

import com.example.there.aroundmenow.base.architecture.vm.RxViewModel
import javax.inject.Inject

class PlaceTypesViewModel @Inject constructor() : RxViewModel<PlaceTypesState>(PlaceTypesState.INITIAL)