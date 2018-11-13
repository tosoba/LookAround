package com.example.there.aroundmenow.util.ext

import com.google.android.material.navigation.NavigationView
import com.jakewharton.rxbinding2.support.design.widget.RxNavigationView
import io.reactivex.disposables.Disposable

fun NavigationView.onItemWithIdSelected(
    onNextId: (Int) -> Unit
): Disposable = RxNavigationView.itemSelections(this)
    .map { it.itemId }
    .subscribe(onNextId)