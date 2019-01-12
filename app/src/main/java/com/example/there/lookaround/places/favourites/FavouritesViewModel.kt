package com.example.there.lookaround.places.favourites

import com.example.there.lookaround.base.architecture.vm.RxViewModel
import javax.inject.Inject

class FavouritesViewModel @Inject constructor() : RxViewModel<FavouritesState>(FavouritesState.INITIAL)