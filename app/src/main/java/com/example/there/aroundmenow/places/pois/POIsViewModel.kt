package com.example.there.aroundmenow.places.pois

import com.example.there.aroundmenow.base.architecture.vm.RxViewModel
import javax.inject.Inject

class POIsViewModel @Inject constructor() : RxViewModel<POIsState>(POIsState.INITIAL)