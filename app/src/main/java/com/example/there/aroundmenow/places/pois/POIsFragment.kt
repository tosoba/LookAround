package com.example.there.aroundmenow.places.pois

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.RxFragment
import com.example.there.aroundmenow.main.MainState


class POIsFragment : RxFragment<MainState, POIsState, POIsViewModel, POIsPresenter>(
    POIsViewModel::class.java,
    MainState::class.java
) {
    override fun observeState() = Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pois, container, false)
    }
}
