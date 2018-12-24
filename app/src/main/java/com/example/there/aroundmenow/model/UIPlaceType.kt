package com.example.there.aroundmenow.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UIPlaceType(
    val query: String,
    val label: String,
    @DrawableRes val drawableId: Int
) : Parcelable

