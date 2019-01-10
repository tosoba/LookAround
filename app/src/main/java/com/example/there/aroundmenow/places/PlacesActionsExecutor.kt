package com.example.there.aroundmenow.places

import com.example.domain.repo.Result
import com.example.domain.repo.model.ReverseGeocodingInfo
import com.example.domain.task.error.ReverseGeocodeLocationError
import com.example.domain.task.impl.ReverseGeocodeLocationTask
import com.example.there.aroundmenow.base.architecture.executor.RxActionsExecutor
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.main.MainState
import com.example.there.aroundmenow.main.MainViewModel
import com.example.there.aroundmenow.util.ext.distanceTo
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import io.reactivex.rxkotlin.withLatestFrom
import javax.inject.Inject

class PlacesActionsExecutor @Inject constructor(
    placesViewModel: PlacesViewModel,
    mainViewModel: MainViewModel,
    private val reverseGeocodeLocationTask: ReverseGeocodeLocationTask
) : RxActionsExecutor.HostAware<PlacesState, MainState, PlacesViewModel, MainViewModel>(placesViewModel, mainViewModel),
    PlacesActions {

    private fun reverseGeocodeLocation(
        latLng: LatLng
    ): Observable<ViewDataState<ReverseGeocodingInfo, ReverseGeocodeLocationError>> =
        reverseGeocodeLocationTask.executeWithInput(
            input = latLng,
            onErrorReturn = { Result.Error(ReverseGeocodeLocationError.Exception(it)) }
        ).toObservable().map {
            when (it) {
                is Result.Value -> ViewDataState.Value(it.value)
                is Result.Error -> ViewDataState.Error(it.error)
            }
        }.startWith(ViewDataState.Loading)

    override fun reverseGeocodeLocation() {
        hostStateObservable.map { it.userLatLng }
            .take(1)
            .withLatestFrom(stateObservable.map { it.lastGeocodingResult })
            .flatMap<ViewDataState<ReverseGeocodingInfo, ReverseGeocodeLocationError>> { (lastUserLatLngState, geocodingResult) ->
                if (lastUserLatLngState is ViewDataState.Value) {
                    if (geocodingResult is ViewDataState.Value) {
                        val geocodingResultLatLng =
                            LatLng(geocodingResult.value.address.latitude, geocodingResult.value.address.longitude)
                        if (lastUserLatLngState.value.distanceTo(geocodingResultLatLng) > 100)
                            reverseGeocodeLocation(lastUserLatLngState.value)
                        else Observable.just(ViewDataState.Value(geocodingResult.value))
                    } else reverseGeocodeLocation(lastUserLatLngState.value)
                } else {
                    if (geocodingResult is ViewDataState.Value) Observable.just(ViewDataState.Value(geocodingResult.value))
                    else Observable.just(ViewDataState.Error(ReverseGeocodeLocationError.GeocodingFailed))
                }
            }
            .mapToStateThenSubscribeAndDisposeWithViewModel({ lastState, result ->
                lastState.copy(lastGeocodingResult = result)
            })
    }
}