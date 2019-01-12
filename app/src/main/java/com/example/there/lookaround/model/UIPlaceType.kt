package com.example.there.lookaround.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UIPlaceType(
    val query: String,
    val label: String,
    @DrawableRes val drawableId: Int
) : Parcelable

