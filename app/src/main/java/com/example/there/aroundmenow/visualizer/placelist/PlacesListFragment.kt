package com.example.there.aroundmenow.visualizer.placelist


import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.list.simpleplaces.SimplePlacesListFragment
import com.example.there.aroundmenow.visualizer.VisualizerState
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
            is ViewDataState.Error -> placesListFragment?.onError()
            is ViewDataState.Loading -> placesListFragment?.onLoading()
        }
    }
}
