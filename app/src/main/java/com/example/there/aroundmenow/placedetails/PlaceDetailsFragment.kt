package com.example.there.aroundmenow.placedetails

import android.os.Bundle
import android.view.View
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.databinding.FragmentPlaceDetailsBinding
import com.example.there.aroundmenow.model.UISimplePlace
import com.example.there.aroundmenow.placedetails.list.PhotosLoadingService
import com.example.there.aroundmenow.placedetails.list.PhotosSliderAdapter
import com.facebook.shimmer.Shimmer
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_place_details.*
import ss.com.bannerslider.Slider


class PlaceDetailsFragment :
    RxFragment.Stateful.HostUnaware.DataBound<PlaceDetailsState, PlaceDetailsActions, FragmentPlaceDetailsBinding>(
        R.layout.fragment_place_details
    ) {

    private val simplePlace: UISimplePlace by lazy {
        arguments!!.getParcelable<UISimplePlace>(ARG_PLACE)
    }

    private var binding: FragmentPlaceDetailsBinding? = null

    private val photosLoadingService: PhotosLoadingService by lazy { PhotosLoadingService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) actions.findPlaceDetails(simplePlace)
    }

    override fun FragmentPlaceDetailsBinding.init() {
        binding = this
        binding?.distanceFromUser = simplePlace.formattedDistanceFromUser
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
                    if (!it.photos.hasValue) actions.findPlacePhotos(it.place.value.id)
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
                    photosLoadingService.photos = it.value
                    place_photos_slider?.setAdapter(PhotosSliderAdapter(it.value))
                    place_photos_slider?.setLoopSlides(true)
                    place_photos_slider?.setInterval(5000)
                }
                is ViewDataState.Error -> {
                    no_place_photos_found_image_view?.visibility = View.VISIBLE
                    place_details_shimmer_layout?.stopShimmer()
                }
                is ViewDataState.Loading -> {
                    no_place_photos_found_image_view?.visibility = View.GONE
                    place_details_shimmer_layout?.startShimmer()
                }
            }
        }
    }

    companion object {
        private const val ARG_PLACE = "ARG_PLACE"

        fun with(place: UISimplePlace): PlaceDetailsFragment = PlaceDetailsFragment().apply {
            arguments = Bundle().apply { putParcelable(ARG_PLACE, place) }
        }
    }
}
