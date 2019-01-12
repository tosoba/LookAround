package com.example.there.lookaround.util.view.bindings

import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

@BindingAdapter("pagerAdapter")
fun bindViewPagerAdapter(viewPager: ViewPager, adapter: PagerAdapter) {
    viewPager.adapter = adapter
}