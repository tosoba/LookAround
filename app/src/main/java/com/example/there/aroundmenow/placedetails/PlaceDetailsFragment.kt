package com.example.there.aroundmenow.placedetails

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.databinding.FragmentPlaceDetailsBinding
import com.example.there.aroundmenow.model.UISimplePlace
import com.example.there.aroundmenow.placedetails.info.PlaceInfoFragment
import com.example.there.aroundmenow.placedetails.map.PlaceDetailsMapFragment
import com.example.there.aroundmenow.placedetails.photoslist.PhotosLoadingService
import com.example.there.aroundmenow.placedetails.photoslist.PhotosSliderAdapter
import com.example.there.aroundmenow.util.view.viewpager.FragmentTitledViewPagerAdapter
import com.facebook.shimmer.Shimmer
import com.google.android.gms.location.places.ui.PlaceAutocomplete
import io.reactivex.Observable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_place_details.*
import ss.com.bannerslider.Slider


class PlaceDetailsFragment :
    RxFragment.Stateful.HostUnaware.DataBound<PlaceDetailsState, PlaceDetailsActions, FragmentPlaceDetailsBinding>(
        R.layout.fragment_place_details
    ) {

    private val simplePlace: UISimplePlace by lazy {
        val args = arguments!!.getParcelable<Arguments>(ARGUMENTS_KEY)
        when (args) {
            is Arguments.PlaceAutocompleteIntent -> args.place
            is Arguments.SimplePlace -> args.place
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

        if (savedInstanceState == null) initFromArguments()
    }

    override fun FragmentPlaceDetailsBinding.init() {
        binding = this
        pagerAdapter = viewPagerAdapter
        distanceFromUser = simplePlace.formattedDistanceFromUser
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

    override fun Observable<PlaceDetailsState>.observe() {
        subscribeWithAutoDispose {
            when (it.place) {
                is ViewDataState.Value -> {
                    place_details_loading_progress_bar?.visibility = View.GONE
                    binding?.place = it.place.value
                    if (it.photos is ViewDataState.Idle) actions.findPlacePhotos(it.place.value.id)
                }
                is ViewDataState.Error -> {
                    place_details_loading_progress_bar?.visibility = View.GONE
                    // TODO: show dialog with msg: "Place details not found" and button to go back
                }
                is ViewDataState.Loading -> {
                    place_details_loading_progress_bar?.visibility = View.VISIBLE
                }
            }
        }

        map { it.photos }.distinctUntilChanged().subscribeWithAutoDispose {
            when (it) {
                is ViewDataState.Value -> {
                    no_place_photos_found_image_view?.visibility = View.GONE
                    place_details_shimmer_layout?.stopShimmer()
                    place_details_shimmer_layout?.visibility = View.GONE
                    photosLoadingService.photos = it.value
                    place_photos_slider?.setAdapter(PhotosSliderAdapter(it.value))
                    place_photos_slider?.setLoopSlides(true)
                    place_photos_slider?.setInterval(5000)
                }
                is ViewDataState.Error -> {
                    no_place_photos_found_image_view?.visibility = View.VISIBLE
                    place_details_shimmer_layout?.stopShimmer()
                    place_details_shimmer_layout?.visibility = View.GONE
                }
                is ViewDataState.Loading -> {
                    no_place_photos_found_image_view?.visibility = View.GONE
                    place_details_shimmer_layout?.startShimmer()
                    place_details_shimmer_layout?.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initFromArguments() {
        val args = arguments!!.getParcelable<Arguments>(ARGUMENTS_KEY)
        when (args) {
            is Arguments.SimplePlace -> actions.findPlaceDetails(args.place)
            is Arguments.PlaceAutocompleteIntent -> actions.setPlace(PlaceAutocomplete.getPlace(activity, args.intent))
        }
    }

    sealed class Arguments : Parcelable {
        @Parcelize
        class SimplePlace(val place: UISimplePlace) : Arguments()

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