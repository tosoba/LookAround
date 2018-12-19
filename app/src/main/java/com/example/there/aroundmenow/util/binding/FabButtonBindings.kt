package com.example.there.aroundmenow.util.binding

import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import mbanje.kurt.fabbutton.FabButton

@BindingAdapter("android:src")
fun bindSrc(fabButton: FabButton, @DrawableRes drawableId: Int) {
    fabButton.setIcon(drawableId, drawableId)
}