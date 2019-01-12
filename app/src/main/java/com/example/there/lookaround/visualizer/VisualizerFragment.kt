package com.example.there.lookaround.visualizer

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.example.data.preferences.AppPreferences
import com.example.there.lookaround.R
import com.example.there.lookaround.base.architecture.view.RxFragment
import com.example.there.lookaround.base.architecture.view.ViewDataState
import com.example.there.lookaround.databinding.FragmentVisualizerBinding
import com.example.there.lookaround.list.simpleplaces.SimplePlacesListEvent
import com.example.there.lookaround.main.LocationUnavailableError
import com.example.there.lookaround.main.MainState
import com.example.there.lookaround.model.UIPlaceType
import com.example.there.lookaround.model.UISimplePlace
import com.example.there.lookaround.placedetails.PlaceDetailsFragment
import com.example.there.lookaround.util.event.EventTags
import com.example.there.lookaround.util.event.TaggedEvent
import com.example.there.lookaround.util.ext.checkItem
import com.example.there.lookaround.util.ext.mainActivity
import com.example.there.lookaround.util.ext.plusAssign
import com.example.there.lookaround.util.lifecycle.EventBusComponent
import com.example.there.lookaround.util.view.viewpager.FragmentViewPagerAdapter
import com.example.there.lookaround.visualizer.camera.CameraFragment
import com.example.there.lookaround.visualizer.map.MapZoomToPlaceEvent
import com.example.there.lookaround.visualizer.map.VisualizerMapFragment
import com.example.there.lookaround.visualizer.placelist.PlacesListFragment
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.withLatestFrom
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_visualizer.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class VisualizerFragment :
    RxFragment.Stateful.HostAware.DataBound<VisualizerState, MainState, Unit, VisualizerActions, FragmentVisualizerBinding>(
        R.layout.fragment_visualizer,
        HostAwarenessMode.ACTIVITY_ONLY
    ) {

    private val viewPagerAdapter by lazy {
        FragmentViewPagerAdapter(
            manager = childFragmentManager,
            fragments = arrayOf(
                CameraFragment(),
                VisualizerMapFragment(),
                PlacesListFragment()
            )
        )
    }

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += EventBusComponent(this)
        if (savedInstanceState == null) initFromArguments()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null && appPreferences.defaultVisualizer == "1") {
            visualizer_view_pager.postDelayed({ visualizer_view_pager.setCurrentItem(1, false) }, 100)
            visualizer_bottom_navigation_view?.checkItem(1)
        }
    }

    override fun FragmentVisualizerBinding.init() {
        pagerAdapter = viewPagerAdapter
    }

    override fun Observable<MainState>.observeActivity() {
        val args = arguments!!.getParcelable<Arguments>(ARGUMENTS_KEY)
        if (args is VisualizerFragment.Arguments.PlaceType) {
            Observable.combineLatest(
                map { it.userLatLng },
                map { it.connectedToInternet }.filter { it.hasValue }.map { it as ViewDataState.Value },
                BiFunction<ViewDataState<LatLng, LocationUnavailableError>, ViewDataState.Value<Boolean>, Pair<ViewDataState<LatLng, LocationUnavailableError>, ViewDataState.Value<Boolean>>> { userLatLngState, connected ->
                    Pair(userLatLngState, connected)
                }
            ).withLatestFrom(observableStateHolder.observableState.map {
                it.places
            }).subscribeWithAutoDispose { (mainState, places) ->
                if (!places.hasValue && places !is ViewDataState.Loading) {
                    val (userLatLng, connected) = mainState
                    if (connected.value) {
                        if (userLatLng is ViewDataState.Value)
                            actions.findNearbyPlacesOfType(args.placeType)
                        else actions.onUserLatLngUnavailable()
                    } else actions.onNoInternetConnection()
                }
            }
        }
    }

    override fun observeViews() {
        visualizer_bottom_navigation_view.onItemWithIdSelected {
            visualizer_view_pager.currentItem = viewPagerItemIndexes[it]!!
        }

        visualizer_view_pager.onPageSelected {
            visualizer_bottom_navigation_view?.checkItem(it)
        }
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPlacesListEvent(taggedEvent: TaggedEvent<SimplePlacesListEvent>) {
        if (taggedEvent.tag == EventTags.FromSimpleListToVisualizer) {
            when (taggedEvent.event) {
                is SimplePlacesListEvent.DetailsRequest -> mainActivity?.showFragment(
                    PlaceDetailsFragment.with(PlaceDetailsFragment.Arguments.SimplePlace(taggedEvent.event.place)),
                    true
                )

                is SimplePlacesListEvent.VisualizationRequest -> {
                    showMapFragment()
                    EventBus.getDefault().post(MapZoomToPlaceEvent(taggedEvent.event.place))
                }
            }
        }
    }

    private fun initFromArguments() {
        val args = arguments!!.getParcelable<Arguments>(ARGUMENTS_KEY)
        if (args is Arguments.Places) actions.setPlaces(args.places)
    }

    private fun showMapFragment() {
        visualizer_view_pager.currentItem = viewPagerItemIndexes[R.id.bottom_navigation_map_item]!!
    }

    sealed class Arguments : Parcelable {
        @Parcelize
        data class Places(val places: List<UISimplePlace>) : Arguments()

        @Parcelize
        data class PlaceType(val placeType: UIPlaceType) : Arguments()
    }

    companion object {
        private val viewPagerItemIndexes = mapOf(
            R.id.bottom_navigation_camera_item to 0,
            R.id.bottom_navigation_map_item to 1,
            R.id.bottom_navigation_places_list_item to 2
        )

        private const val ARGUMENTS_KEY = "ARGUMENTS_KEY"

        fun with(
            arguments: Arguments
        ): VisualizerFragment = VisualizerFragment().apply {
            this.arguments = Bundle().apply {
                putParcelable(ARGUMENTS_KEY, arguments)
            }
        }
    }
}
