package com.example.there.aroundmenow.util.ext

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView
import io.reactivex.disposables.Disposable

fun BottomNavigationView.onItemWithIdSelected(
    onNextId: (Int) -> Unit
): Disposable = RxBottomNavigationView.itemSelections(this)
    .map { it.itemId }
    .skip(1)
    .distinctUntilChanged()
    .subscribe(onNextId)
