package com.example.there.lookaround.places.pois

import com.example.there.lookaround.base.architecture.vm.RxViewModel
import javax.inject.Inject

class POIsViewModel @Inject constructor() : RxViewModel<POIsState>(POIsState.INITIAL)