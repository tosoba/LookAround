package com.example.there.lookaround.util.ext

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.rxbinding2.support.design.widget.RxBottomNavigationView
import io.reactivex.Observable

val BottomNavigationView.itemWithIdSelected: Observable<Int>
    get() = RxBottomNavigationView.itemSelections(this)
        .map { it.itemId }
        .skip(1)
        .distinctUntilChanged()

fun BottomNavigationView.checkItem(index: Int) {
    menu.getItem(index)?.isChecked = true
}
