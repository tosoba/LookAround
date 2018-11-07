package com.example.there.aroundmenow.places

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.databinding.ActivityPlacesBinding
import com.example.there.aroundmenow.places.placetypes.PlaceTypesFragment
import com.example.there.aroundmenow.places.pois.PoisFragment
import com.example.there.aroundmenow.util.ext.onItemWithIdSelected
import com.example.there.aroundmenow.util.ext.plusAssign
import com.example.there.aroundmenow.util.lifecycle.UiDisposablesComponent
import com.example.there.aroundmenow.util.view.FragmentViewPagerAdapter
import kotlinx.android.synthetic.main.activity_places.*

class PlacesActivity : AppCompatActivity() {

    private val viewPagerItemIndexes = mapOf(R.id.navigation_place_types to 0, R.id.navigation_pois to 1)

    private val viewPagerAdapter by lazy {
        FragmentViewPagerAdapter(
            manager = supportFragmentManager,
            fragments = arrayOf(PlaceTypesFragment(), PoisFragment())
        )
    }

    private val disposablesComponent = UiDisposablesComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()

        lifecycle += disposablesComponent
        bindBottomNavigationToViewPager()
    }

    private fun setupDataBinding() {
        val binding: ActivityPlacesBinding = DataBindingUtil.setContentView(this, R.layout.activity_places)
        binding.pagerAdapter = viewPagerAdapter
    }

    private fun bindBottomNavigationToViewPager() {
        disposablesComponent += places_bottom_navigation_view.onItemWithIdSelected {
            places_view_pager.currentItem = viewPagerItemIndexes[it]!!
        }
    }
}
