package com.example.there.aroundmenow.places.favourites

import android.os.Bundle
import android.util.Log
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.view.RxFragment
import com.example.there.aroundmenow.base.architecture.view.ViewDataState
import io.reactivex.Observable


class FavouritesFragment : RxFragment.Stateful.HostUnaware.WithLayout<FavouritesState, FavouritesActions>(
    R.layout.fragment_favourites
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) actions.getFavouritesPlaces()
    }

    override fun Observable<FavouritesState>.observe() = map { it.places }
        .distinctUntilChanged()
        .subscribeWithAutoDispose {
            when (it) {
                is ViewDataState.Value -> {
                    Log.e("PLACES", it.value.size.toString())
                }
                is ViewDataState.Error -> {
                    Log.e("PLACES ERROR", it.error.throwable.message)
                }
            }
        }
}
