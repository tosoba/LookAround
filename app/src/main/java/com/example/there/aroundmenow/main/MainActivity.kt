package com.example.there.aroundmenow.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.there.aroundmenow.R
import com.example.there.aroundmenow.base.architecture.RxActivity
import com.example.there.aroundmenow.places.PlacesFragment
import com.example.there.aroundmenow.search.SearchFragment
import com.example.there.aroundmenow.util.ext.onItemWithIdSelected
import com.example.there.aroundmenow.util.ext.onTextChanged
import com.example.there.aroundmenow.util.ext.toggle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : RxActivity<MainState, MainViewModel, MainPresenter>(MainViewModel::class.java) {

    private val currentlyShowingFragment: Fragment?
        get() = supportFragmentManager?.findFragmentById(backStackLayoutId)

    private val onSearchViewActionExpandListener = object : MenuItem.OnActionExpandListener {
        override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
            drawer_layout.closeDrawers()
            showSearchFragmentIfNotAlreadyShown()
            return true
        }

        override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
            removeFragment()
            return true
        }
    }

    private val onBackStackChangedListener = FragmentManager.OnBackStackChangedListener {
        updateHomeAsUpIndicator()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showPlacesFragmentIfNotAlreadyShown()

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_24dp)
        }

        supportFragmentManager.addOnBackStackChangedListener(onBackStackChangedListener)

        uiDisposables += drawer_navigation_view.onItemWithIdSelected {
            drawer_layout.closeDrawers()
        }
    }

    override fun initializeLayout() = setContentView(R.layout.activity_main)

    override fun onResume() {
        super.onResume()
        updateHomeAsUpIndicator()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        val searchViewMenuItem = menu.findItem(R.id.search_places_toolbar_item)
        searchViewMenuItem.setOnActionExpandListener(onSearchViewActionExpandListener)
        uiDisposables += (searchViewMenuItem.actionView as SearchView).onTextChanged {
            presenter.updatePlacesQuery(it.toString())
        }

        return true
    }

    @SuppressLint("RtlHardcoded")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        android.R.id.home -> {
            if (supportFragmentManager.backStackEntryCount > 0) onBackPressed()
            else drawer_layout?.toggle(Gravity.LEFT)
            true
        }
        else -> false
    }

    override fun observeState() = Unit

    fun showFragment(fragment: Fragment, addToBackStack: Boolean) = with(supportFragmentManager.beginTransaction()) {
        setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        replace(backStackLayoutId, fragment)
        if (addToBackStack) addToBackStack(null)
        commit()
    }

    fun removeFragment() = supportFragmentManager.popBackStack()

    private fun showPlacesFragmentIfNotAlreadyShown() {
        if (currentlyShowingFragment == null) with(supportFragmentManager.beginTransaction()) {
            add(backStackLayoutId, PlacesFragment())
            commit()
        }
    }

    private fun showSearchFragmentIfNotAlreadyShown() {
        if (currentlyShowingFragment !is SearchFragment)
            showFragment(SearchFragment(), true)
    }

    private fun updateHomeAsUpIndicator() = if (supportFragmentManager.backStackEntryCount > 0)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)
    else supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_24dp)

    companion object {
        private const val backStackLayoutId = R.id.container
    }
}
