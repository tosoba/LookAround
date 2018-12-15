package com.example.there.aroundmenow.base.architecture.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.there.aroundmenow.util.ext.plusAssign
import com.example.there.aroundmenow.util.lifecycle.UiDisposablesComponent
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView
import com.jakewharton.rxbinding2.support.design.widget.RxNavigationView
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface ViewObserver {

    val uiDisposables: UiDisposablesComponent

    fun Disposable.disposeOnDestroy() {
        uiDisposables += this
    }

    fun <T> Observable<T>.subscribeWithAutoDispose(
        onNext: (T) -> Unit
    ) = subscribe(onNext).disposeOnDestroy()

    fun observeViews() = Unit

    fun NavigationView.onItemWithIdSelected(
        onNextId: (Int) -> Unit
    ) = RxNavigationView.itemSelections(this)
        .map { it.itemId }
        .subscribeWithAutoDispose(onNextId)

    fun SearchView.onTextChanged(
        onTextChanged: (CharSequence) -> Unit
    ) = RxSearchView.queryTextChanges(this)
        .distinctUntilChanged()
        .filter { it.isNotEmpty() }
        .subscribeWithAutoDispose(onTextChanged)

    fun BottomNavigationView.onItemWithIdSelected(
        onNextId: (Int) -> Unit
    ) = RxBottomNavigationView.itemSelections(this)
        .map { it.itemId }
        .skip(1)
        .distinctUntilChanged()
        .subscribeWithAutoDispose(onNextId)
}

abstract class ViewObservingActivity : AppCompatActivity(), ViewObserver {

    override val uiDisposables = UiDisposablesComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += uiDisposables
        observeViews()
    }
}

abstract class ViewObservingFragment : Fragment(), ViewObserver {

    override val uiDisposables = UiDisposablesComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle += uiDisposables
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViews()
    }
}

