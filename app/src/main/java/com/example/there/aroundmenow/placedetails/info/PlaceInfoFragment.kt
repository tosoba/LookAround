package com.example.there.aroundmenow.placedetails.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import com.example.there.aroundmenow.databinding.FragmentPlaceInfoBinding
import com.example.there.aroundmenow.placedetails.PlaceDetailsState
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_place_info.*
import org.greenrobot.eventbus.EventBus


class PlaceInfoFragment : RxFragment.Stateless.HostAware.DataBound<Unit, PlaceDetailsState, FragmentPlaceInfoBinding>(
    R.layout.fragment_place_info,
    HostAwarenessMode.PARENT_FRAGMENT_ONLY
) {

    private lateinit var binding: FragmentPlaceInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        place_info_recycler_view?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun FragmentPlaceInfoBinding.init() {
        binding = this
    }

    override fun Observable<PlaceDetailsState>.observeParentFragment() = map { it.place }
        .distinctUntilChanged()
        .subscribeWithAutoDispose { placeState ->
            if (placeState is ViewDataState.Value) {
                place_info_recycler_view?.adapter = PlaceInfoAdapter(placeState.value)
                binding.place = placeState.value

                place_info_website_fab?.setOnClickListener { _ ->
                    placeState.value.websiteUri?.let { openPlaceWebsite(it) }
                }

                place_info_google_maps_fab?.setOnClickListener { launchGoogleMaps(placeState.value.latLng) }

                place_info_add_to_favourites_fab?.setOnClickListener {
                    EventBus.getDefault().post(AddPlaceToFavouritesEvent(placeState.value))
                }
            }
        }

    private fun openPlaceWebsite(uriString: String) = try {
        val uri = Uri.parse(uriString)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    } catch (exception: Exception) {
        Toast.makeText(context, "Failed to open place website.", Toast.LENGTH_LONG).show()
    }

    private fun launchGoogleMaps(latLng: LatLng) {
        try {
            val intentUri = Uri.parse("geo:${latLng.latitude},${latLng.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (activity?.packageManager != null && mapIntent.resolveActivity(activity!!.packageManager) != null)
                startActivity(mapIntent)
        } catch (exception: Exception) {
            Toast.makeText(context, "Failed to open Google Maps.", Toast.LENGTH_LONG).show()
        }
    }
}
