package com.example.there.aroundmenow.util.view.bindings

import android.databinding.BindingAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

@BindingAdapter("pagerAdapter")
fun bindViewPagerAdapter(viewPager: ViewPager, adapter: PagerAdapter) {
    viewPager.adapter = adapter
}