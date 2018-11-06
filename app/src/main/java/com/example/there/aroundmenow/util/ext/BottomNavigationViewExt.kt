package com.example.there.aroundmenow.util.ext

import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView
import io.reactivex.disposables.Disposable

fun BottomNavigationView.itemSelected(
    onItemSelected: (MenuItem) -> Unit
): Disposable = RxBottomNavigationView.itemSelections(this)
    .distinctUntilChanged()
    .subscribe { onItemSelected(it) }
