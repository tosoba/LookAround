package com.example.there.aroundmenow.util.ext

import com.google.android.material.navigation.NavigationView
import com.jakewharton.rxbinding2.support.design.widget.RxNavigationView
import io.reactivex.Observable

val NavigationView.itemWithIdSelected: Observable<Int>
    get() = RxNavigationView.itemSelections(this)
        .map { it.itemId }