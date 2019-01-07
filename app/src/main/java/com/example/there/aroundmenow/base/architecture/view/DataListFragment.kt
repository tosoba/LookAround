package com.example.there.aroundmenow.base.architecture.view

interface DataListFragment<V> {
    fun onValue(value: V)
    fun onError(message: String)
    fun onLoading()
}