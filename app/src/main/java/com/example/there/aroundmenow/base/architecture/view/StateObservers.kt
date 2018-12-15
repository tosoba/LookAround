package com.example.there.aroundmenow.base.architecture.view

import io.reactivex.Observable

interface StateObserver<State> {
    fun Observable<State>.observe()
}

interface HostStateObserver<HostState> {
    fun Observable<HostState>.observeHost()
}