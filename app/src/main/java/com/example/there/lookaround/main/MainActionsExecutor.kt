package com.example.there.lookaround.main

import com.example.there.lookaround.base.architecture.executor.RxActionsExecutor
import com.example.there.lookaround.base.architecture.view.ViewDataState
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class MainActionsExecutor @Inject constructor(
    mainViewModel: MainViewModel
) : RxActionsExecutor.HostUnaware<MainState, MainViewModel>(mainViewModel), MainActions {

    override fun setConnectedToInternet(connected: Boolean) = mutateState {
        it.copy(connectedToInternet = ViewDataState.Value(connected))
    }

    override fun setUserLatLng(latLng: LatLng) = mutateState {
        it.copy(userLatLng = ViewDataState.Value(latLng))
    }
}