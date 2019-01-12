package com.example.there.lookaround.util.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentTitledViewPagerAdapter(
    manager: FragmentManager,
    private val fragmentsAndTitles: Array<Pair<Fragment, String>>
) : FragmentViewPagerAdapter(manager, fragmentsAndTitles.map { it.first }.toTypedArray()) {

    override fun getPageTitle(position: Int): CharSequence? = fragmentsAndTitles[position].second
}