package com.example.there.aroundmenow.places.favourites

import com.example.there.aroundmenow.base.architecture.vm.RxViewModel
import javax.inject.Inject

class FavouritesViewModel @Inject constructor() : RxViewModel<FavouritesState>(FavouritesState.INITIAL)