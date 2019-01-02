package com.example.there.aroundmenow.util.view.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

open class FragmentViewPagerAdapter(
    manager: FragmentManager,
    protected val fragments: Array<Fragment>
) : FragmentPagerAdapter(manager) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size
}