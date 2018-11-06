package com.example.there.aroundmenow.util.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentViewPagerAdapter(
    manager: FragmentManager,
    private val fragments: Array<Fragment>
) : FragmentPagerAdapter(manager) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size
}