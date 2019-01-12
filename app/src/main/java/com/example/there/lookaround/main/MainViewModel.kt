package com.example.there.lookaround.main

import com.example.there.lookaround.base.architecture.vm.RxViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : RxViewModel<MainState>(MainState.INITIAL)