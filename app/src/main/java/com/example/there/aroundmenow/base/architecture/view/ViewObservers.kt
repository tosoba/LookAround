package com.example.there.aroundmenow.base.architecture.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.there.aroundmenow.util.ext.itemWithIdSelected
import com.example.there.aroundmenow.util.ext.pageSelected
import com.example.there.aroundmenow.util.ext.plusAssign
import com.example.there.aroundmenow.util.ext.queryTextChanged
import com.example.there.aroundmenow.util.lifecycle.UiDisposablesComponent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface ViewObserver {

    val uiDisposables: UiDisposablesComponent

    fun Disposable.disposeOnDestroy() {
        uiDisposables += this
    }

    fun <T> Observable<T>.subscribeWithAutoDispose(
        onNext: (T) -> Unit
    ) = subscribe(onNext).disposeOnDestroy()

    fun <T> Single<T>.subscribeWithAutoDispose(
        onNext: (T) -> Unit
    ) = subscribe(onNext).disposeOnDestroy()

    fun observeViews() = Unit

    fun NavigationView.onItemWithIdSelected(
        onNextId: (Int) -> Unit
    ) = itemWithIdSelected.subscribeWithAutoDispose(onNextId)

    fun SearchView.onTextChanged(
        onTextChanged: (CharSequence) -> Unit
    ) = queryTextChanged.subscribeWithAutoDispose(onTextChanged)

    fun BottomNavigationView.onItemWithIdSelected(
        onNextId: (Int) -> Unit
    ) = itemWithIdSelected.subscribeWithAutoDispose(onNextId)

    fun ViewPager.onPageSelected(
        onNext: (Int) -> Unit
    ) = pageSelected.subscribeWithAutoDispose(onNext)
}

abstract class ViewObservingActivity(
    disposalMode: UiDisposablesComponent.DisposalMode = UiDisposablesComponent.DisposalMode.ON_DESTROY
) : AppCompatActivity(), ViewObserver {

    override val uiDisposables = UiDisposablesComponent(disposalMode)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += uiDisposables
        observeViews()
    }
}

abstract class ViewObservingFragment(
    disposalMode: UiDisposablesComponent.DisposalMode = UiDisposablesComponent.DisposalMode.ON_PAUSE
) : Fragment(), ViewObserver {

    override val uiDisposables = UiDisposablesComponent(disposalMode)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += uiDisposables
    }

    override fun onResume() {
        super.onResume()
        observeViews()
    }
}

