package com.example.there.aroundmenow.placedetails.list

import android.graphics.Bitmap
import ss.com.bannerslider.adapters.SliderAdapter
import ss.com.bannerslider.viewholder.ImageSlideViewHolder

class PhotosSliderAdapter(private val photos: List<Bitmap>) : SliderAdapter() {

    override fun getItemCount(): Int = photos.size

    override fun onBindImageSlide(position: Int, imageSlideViewHolder: ImageSlideViewHolder?) {
        imageSlideViewHolder?.bindImageSlide(position.toString())
    }
}

