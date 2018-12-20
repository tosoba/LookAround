package com.example.there.aroundmenow.places.placetypes

import com.example.there.aroundmenow.base.architecture.executor.RxActionsExecutor
import javax.inject.Inject

class PlaceTypesActionsExecutor @Inject constructor(
    placeTypesViewModel: PlaceTypesViewModel
) : RxActionsExecutor.HostUnaware<PlaceTypesState, PlaceTypesViewModel>(placeTypesViewModel), PlaceTypesActions {
}