package com.example.there.aroundmenow.main

import com.example.there.aroundmenow.base.architecture.vm.RxViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : RxViewModel<MainState>(MainState.INITIAL)