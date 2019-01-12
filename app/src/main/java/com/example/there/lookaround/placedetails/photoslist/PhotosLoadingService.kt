package com.example.there.lookaround.placedetails.photoslist

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import ss.com.bannerslider.ImageLoadingService

class PhotosLoadingService : ImageLoadingService {

    var photos: List<Bitmap> = emptyList()

    override fun loadImage(resource: Int, imageView: ImageView) = Unit

    override fun loadImage(url: String, placeHolder: Int, errorDrawable: Int, imageView: ImageView) = Unit

    override fun loadImage(url: String, imageView: ImageView) {
        url.toIntOrNull().let {
            Glide.with(imageView).load(photos[it!!]).into(imageView)
        }
    }
}