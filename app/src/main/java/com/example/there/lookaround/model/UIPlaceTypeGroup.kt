package com.example.there.lookaround.model

import androidx.annotation.DrawableRes
import com.bumptech.glide.request.RequestOptions
import com.example.there.lookaround.util.view.bindings.GlideImageBinding

data class UIPlaceTypeGroup(
    val name: String,
    val placeTypes: List<UIPlaceType>,
    @DrawableRes val drawableId: Int
) {
    val glideSrc: GlideImageBinding
        get() = GlideImageBinding(drawableId, RequestOptions().fitCenter())
}