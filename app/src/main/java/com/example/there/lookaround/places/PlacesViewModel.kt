package com.example.there.lookaround.places

import com.example.there.lookaround.base.architecture.vm.RxViewModel
import javax.inject.Inject

class PlacesViewModel @Inject constructor() : RxViewModel<PlacesState>(PlacesState.INITIAL)