package com.example.there.aroundmenow.placedetails

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import com.example.data.util.ext.formattedDistanceTo
import com.example.domain.task.error.FindPlaceDetailsError
import com.example.domain.task.error.FindPlacePhotosError
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.databinding.FragmentPlaceDetailsBinding
import com.example.there.aroundmenow.main.MainState
import com.example.there.aroundmenow.model.UIPlace
import com.example.there.aroundmenow.model.UISimplePlace
import com.example.there.aroundmenow.placedetails.info.AddPlaceToFavouritesEvent
import com.example.there.aroundmenow.placedetails.info.PlaceInfoFragment
import com.example.there.aroundmenow.placedetails.map.PlaceDetailsMapFragment
import com.example.there.aroundmenow.placedetails.photoslist.PhotosLoadingService
import com.example.there.aroundmenow.placedetails.photoslist.PhotosSliderAdapter
import com.example.there.aroundmenow.util.ext.*
import com.example.there.aroundmenow.util.lifecycle.EventBusComponent
import com.example.there.aroundmenow.util.view.viewpager.FragmentTitledViewPagerAdapter
import com.facebook.shimmer.Shimmer
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.withLatestFrom
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_place_details.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import ss.com.bannerslider.Slider


class PlaceDetailsFragment :
    RxFragment.Stateful.HostAware.DataBound<PlaceDetailsState, MainState, Unit, PlaceDetailsActions, FragmentPlaceDetailsBinding>(
        R.layout.fragment_place_details,
        HostAwarenessMode.ACTIVITY_ONLY
    ) {

    private val simplePlace: UISimplePlace by lazy {
        val args = arguments!!.getParcelable<Arguments>(ARGUMENTS_KEY)
        when (args) {
            is Arguments.PlaceAutocompleteIntent -> args.place
            is Arguments.SimplePlace -> args.place
            is Arguments.PlaceDetails -> args.place
        }
    }

    private var binding: FragmentPlaceDetailsBinding? = null

    private val photosLoadingService: PhotosLoadingService by lazy { PhotosLoadingService() }

    private val viewPagerAdapter: FragmentTitledViewPagerAdapter by lazy {
        FragmentTitledViewPagerAdapter(
            childFragmentManager, arrayOf(
                PlaceInfoFragment() to "Info",
                PlaceDetailsMapFragment.with(simplePlace) to "Map"
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle += EventBusComponent(this)

        if (savedInstanceState == null) initFromArguments()
    }

    override fun FragmentPlaceDetailsBinding.init() {
        binding = this
        pagerAdapter = viewPagerAdapter
        distanceFromUser = simplePlace.formattedDistanceFromUser
        placeDetailsNameTextView.text = simplePlace.name
        placeDetailsTabLayout.setupWithViewPager(placeDetailsViewPager)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Slider.init(photosLoadingService)
        place_details_shimmer_layout?.setShimmer(Shimmer.AlphaHighlightBuilder().build())
    }

    override fun onPause() {
        place_details_shimmer_layout?.stopShimmer()
        super.onPause()
    }

    override fun Observable<MainState>.observeActivity() {
        val args = arguments!!.getParcelable<Arguments>(ARGUMENTS_KEY)

        if (args is Arguments.SimplePlace) map { it.connectedToInternet }
            .valuesOnly()
            .withLatestFrom(observableStateHolder.observableState.map { it.place })
            .subscribeWithAutoDispose { (connected, placeState) ->
                if (!placeState.hasValue) {
                    if (connected.value) actions.findPlaceDetails(args.place)
                    else actions.onNoInternetConnectionWhenLoadingPlaceDetails()
                }
            }

        //TODO: see if this works after actual location updates are implemented
        map { it.userLatLng }.valuesOnly().subscribeWithAutoDispose { userLatLng ->
            binding?.let {
                it.distanceFromUser = userLatLng.value.formattedDistanceTo(simplePlace.latLng)
            }
        }
    }

    override fun Observable<PlaceDetailsState>.observe() {
        Observable.combineLatest(
            map { it.place },
            observableActivityState.map { it.connectedToInternet }.valuesOnly(),
            BiFunction<ViewDataState<UIPlace, FindPlaceDetailsError>, ViewDataState.Value<Boolean>, Pair<ViewDataState<UIPlace, FindPlaceDetailsError>, ViewDataState.Value<Boolean>>> { placeState, connected ->
                Pair(placeState, connected)
            }
        ).withLatestFrom(map { it.photos }).subscribeWithAutoDispose { (placeAndConnectionState, photosState) ->
            val (placeState, connected) = placeAndConnectionState
            when (placeState) {
                is ViewDataState.Value -> {
                    place_details_error_card_view?.hide()
                    place_details_loading_progress_bar?.hide()
                    binding?.place = placeState.value
                    if (photosState.shouldLoadPhotos) {
                        if (connected.value) actions.findPlacePhotos(placeState.value.id)
                        else actions.onNoInternetConnectionWhenLoadingPhotos()
                    }
                }
                is ViewDataState.Loading -> {
                    place_details_error_card_view?.hide()
                    place_details_loading_progress_bar?.show()
                }
                is ViewDataState.Error -> {
                    place_details_loading_progress_bar?.hide()
                    when (placeState.error) {
                        is FindPlaceDetailsError.PlaceDetailsNotFound -> {
                            //TODO: show dialog with msg: "Place details not found" and button to go back - or don't hide the cardview and show button to go back on it
                            place_details_error_card_view?.hide()
                        }
                        is FindPlaceDetailsError.NoInternetConnection -> {
                            place_details_error_card_view?.show()
                            place_details_error_text_view?.text = getString(R.string.unable_to_load_place_details)
                            onPlacePhotosLoadingError()
                        }
                    }
                }
            }
        }

        map { it.photos }.distinctUntilChanged().subscribeWithAutoDispose {
            when (it) {
                is ViewDataState.Value -> {
                    no_place_photos_found_image_view?.hide()
                    place_details_shimmer_layout?.stopShimmer()
                    place_details_shimmer_layout?.hide()
                    photosLoadingService.photos = it.value
                    place_photos_slider?.setAdapter(PhotosSliderAdapter(it.value))
                    place_photos_slider?.setLoopSlides(true)
                    place_photos_slider?.setInterval(5000)
                }
                is ViewDataState.Error -> onPlacePhotosLoadingError()
                is ViewDataState.Loading -> {
                    no_place_photos_found_image_view?.hide()
                    place_details_shimmer_layout?.show()
                    place_details_shimmer_layout?.startShimmer()
                }
            }
        }
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAddPlaceToFavouritesEvent(event: AddPlaceToFavouritesEvent) {
        actions.addPlaceToFavourites(event.place) {
            Toast.makeText(context, "${event.place.name} added to favourites.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initFromArguments() {
        val args = arguments!!.getParcelable<Arguments>(ARGUMENTS_KEY)
        when (args) {
            is Arguments.PlaceAutocompleteIntent -> actions.setPlace(
                PlaceAutocomplete.getPlace(activity, args.intent).ui
            )
            is Arguments.PlaceDetails -> actions.setPlace(args.placeDetails)
        }
    }

    private fun onPlacePhotosLoadingError() {
        no_place_photos_found_image_view?.show()
        place_details_shimmer_layout?.stopShimmer()
        place_details_shimmer_layout?.hide()
    }

    private val ViewDataState<List<Bitmap>, FindPlacePhotosError>.shouldLoadPhotos: Boolean
        get() = this is ViewDataState.Idle || (this is ViewDataState.Error && error is FindPlacePhotosError.NoInternetConnection)

    sealed class Arguments : Parcelable {
        @Parcelize
        class SimplePlace(val place: UISimplePlace) : Arguments()

        @Parcelize
        class PlaceDetails(val placeDetails: UIPlace, val place: UISimplePlace) : Arguments()

        @Parcelize
        class PlaceAutocompleteIntent(val intent: Intent, val place: UISimplePlace) : Arguments()
    }

    companion object {
        private const val ARGUMENTS_KEY = "ARGUMENTS_KEY"

        fun with(arguments: Arguments): PlaceDetailsFragment = PlaceDetailsFragment().apply {
            this.arguments = Bundle().apply { putParcelable(ARGUMENTS_KEY, arguments) }
        }
    }
}