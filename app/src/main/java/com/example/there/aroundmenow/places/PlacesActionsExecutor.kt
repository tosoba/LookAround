package com.example.there.aroundmenow.places

import com.example.domain.repo.Result
import com.example.domain.task.error.ReverseGeocodeLocationError
import com.example.domain.task.impl.ReverseGeocodeLocationTask
import com.example.there.aroundmenow.base.architecture.executor.RxActionsExecutor
import com.example.there.aroundmenow.base.architecture.view.ViewData
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class PlacesActionsExecutor @Inject constructor(
    placesViewModel: PlacesViewModel,
    private val reverseGeocodeLocationTask: ReverseGeocodeLocationTask
) : RxActionsExecutor<PlacesState, PlacesViewModel>(placesViewModel), PlacesActions {

    override fun reverseGeocodeLocation(latLng: LatLng) {
        mutateState {
            it.copy(lastGeocodingResult = ViewData.Loading)
        }

        reverseGeocodeLocationTask.executeWithInput(
            input = latLng,
            onErrorReturn = { Result.Error(ReverseGeocodeLocationError.Exception(it)) }
        ).mapToStateThenSubscribeAndDisposeWithViewModel({ _, result ->
            when (result) {
                is Result.Value -> PlacesState(ViewData.Value(result.value))
                is Result.Error -> PlacesState(ViewData.Error(result.error))
            }
        })
    }
}