package com.example.there.aroundmenow.places.placetypes

import com.example.there.aroundmenow.base.architecture.RxActionsExecutor
import javax.inject.Inject

class PlaceTypesActionsExecutor @Inject constructor(
    placeTypesViewModel: PlaceTypesViewModel
) : RxActionsExecutor<PlaceTypesState, PlaceTypesViewModel>(placeTypesViewModel), PlaceTypesActions {
}