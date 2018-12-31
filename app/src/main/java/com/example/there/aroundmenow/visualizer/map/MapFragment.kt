package com.example.there.aroundmenow.visualizer.map


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.util.ext.plusAssign
import com.example.there.aroundmenow.util.lifecycle.EventBusComponent
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MapFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += EventBusComponent(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    @Suppress("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onZoomToPlaceEvent(event: MapZoomToPlaceEvent) {
        //TODO: zoom to place
    }
}
