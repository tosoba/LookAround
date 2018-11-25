package com.example.there.aroundmenow.base.architecture

interface RxViewModelHolder<State, VM : RxViewModel<State>> {
    var viewModel: VM
}