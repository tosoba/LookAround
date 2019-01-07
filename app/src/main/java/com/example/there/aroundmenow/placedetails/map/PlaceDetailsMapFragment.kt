package com.example.there.aroundmenow.placedetails.map


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.model.UISimplePlace
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions


class PlaceDetailsMapFragment : Fragment(), OnMapReadyCallback {

    private val simplePlace: UISimplePlace by lazy {
        arguments!!.getParcelable<UISimplePlace>(ARG_SIMPLE_PLACE)
    }

    private lateinit var map: GoogleMap

    private val googleMapFragment: SupportMapFragment?
        get() = if (isAdded)
            childFragmentManager.findFragmentById(R.id.place_details_google_map_fragment) as? SupportMapFragment
        else null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_place_details_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        googleMapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                googleMapFragment?.context, R.raw.map_style
            )
        )
        googleMap.addMarker(simplePlace.markerOptions)
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(simplePlace.latLng, 14f))
    }

    companion object {
        private const val ARG_SIMPLE_PLACE = "ARG_SIMPLE_PLACE"

        fun with(
            simplePlace: UISimplePlace
        ): PlaceDetailsMapFragment = PlaceDetailsMapFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_SIMPLE_PLACE, simplePlace)
            }
        }
    }
}
