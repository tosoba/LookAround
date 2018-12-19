package com.example.there.aroundmenow.util.binding

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("android:src")
fun bindSrc(imageView: ImageView, @DrawableRes drawableId: Int) {
    imageView.setImageResource(drawableId)
}