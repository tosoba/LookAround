package com.example.there.lookaround.util.ext

import androidx.appcompat.widget.SearchView
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import io.reactivex.Observable

val SearchView.queryTextChanged: Observable<CharSequence>
    get() = RxSearchView.queryTextChanges(this)
        .distinctUntilChanged()
        .filter { it.isNotEmpty() }