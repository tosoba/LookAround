package com.example.there.lookaround.visualizer.placelist


import com.example.domain.task.error.FindNearbyPlacesError
import com.example.there.lookaround.R
import com.example.there.lookaround.base.architecture.view.RxFragment
import com.example.there.lookaround.base.architecture.view.ViewDataState
import com.example.there.lookaround.list.simpleplaces.SimplePlacesListFragment
import com.example.there.lookaround.visualizer.VisualizerState
import io.reactivex.Observable

class PlacesListFragment : RxFragment.Stateless.HostAware.WithLayout<Unit, VisualizerState>(
    R.layout.fragment_places_list,
    HostAwarenessMode.PARENT_FRAGMENT_ONLY
) {
    private val placesListFragment: SimplePlacesListFragment?
        get() = if (isAdded)
            childFragmentManager.findFragmentById(R.id.visualizer_places_list_fragment) as? SimplePlacesListFragment
        else null

    override fun Observable<VisualizerState>.observeParentFragment() = subscribeWithAutoDispose { state ->
        when (state.places) {
            is ViewDataState.Value -> placesListFragment?.onValue(state.places.value)
            is ViewDataState.Error -> when (state.places.error) {
                is FindNearbyPlacesError.NoInternetConnection -> placesListFragment?.onError(
                    getString(R.string.unable_to_load_nearby_places_no_internet_connection)
                )
                is FindNearbyPlacesError.UserLocationUnknown -> placesListFragment?.onError(
                    getString(R.string.unable_to_load_nearby_places_no_location)
                )
                is FindNearbyPlacesError.NoPlacesFound -> placesListFragment?.onError(
                    getString(R.string.no_nearby_places_of_requested_type_found)
                )
            }
            is ViewDataState.Loading -> placesListFragment?.onLoading()
        }
    }
}
