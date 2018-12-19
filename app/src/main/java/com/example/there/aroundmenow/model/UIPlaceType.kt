package com.example.there.aroundmenow.model

import androidx.annotation.DrawableRes

data class UIPlaceType(
    val query: String,
    val label: String,
    @DrawableRes val drawableId: Int
)

