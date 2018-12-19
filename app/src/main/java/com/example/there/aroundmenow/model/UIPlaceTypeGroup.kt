package com.example.there.aroundmenow.model

import androidx.annotation.DrawableRes

data class UIPlaceTypeGroup(
    val name: String,
    val placeTypes: List<UIPlaceType>,
    @DrawableRes val drawableId: Int
)