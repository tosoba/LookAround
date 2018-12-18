package com.example.there.aroundmenow.places

import com.example.there.aroundmenow.base.architecture.vm.RxViewModel
import javax.inject.Inject

class PlacesViewModel @Inject constructor() : RxViewModel<PlacesState>(PlacesState.INITIAL)