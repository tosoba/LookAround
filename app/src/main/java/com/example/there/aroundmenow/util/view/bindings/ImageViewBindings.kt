package com.example.there.aroundmenow.util.view.bindings

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("android:src")
fun bindSrc(imageView: ImageView, @DrawableRes drawableId: Int) {
    imageView.setImageResource(drawableId)
}

class GlideImageBinding(
    @DrawableRes val drawableId: Int,
    val options: RequestOptions
)

@BindingAdapter("srcGlide")
fun bindSrcGlide(imageView: ImageView, binding: GlideImageBinding) {
    Glide.with(imageView)
        .applyDefaultRequestOptions(binding.options)
        .load(binding.drawableId)
        .into(imageView)
}