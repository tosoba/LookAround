package com.example.there.lookaround.base.architecture.view

import io.reactivex.Observable

interface StateObserver<State> {
    fun Observable<State>.observe() = Unit
}

interface HostStateObserver<ActivityState, ParentFragmentState> {
    fun Observable<ActivityState>.observeActivity() = Unit
    fun Observable<ParentFragmentState>.observeParentFragment() = Unit
}