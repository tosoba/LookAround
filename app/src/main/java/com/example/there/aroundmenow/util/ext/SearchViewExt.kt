package com.example.there.aroundmenow.util.ext

import androidx.appcompat.widget.SearchView
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import io.reactivex.disposables.Disposable

fun SearchView.onTextChanged(
    onTextChanged: (CharSequence) -> Unit
): Disposable = RxSearchView.queryTextChanges(this)
    .distinctUntilChanged()
    .filter { it.isNotEmpty() }
    .subscribe(onTextChanged)