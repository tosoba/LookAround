package com.example.there.aroundmenow.main

import com.example.there.aroundmenow.base.architecture.RxActionsExecutor
import javax.inject.Inject

class MainActionsExecutor @Inject constructor(
    mainViewModel: MainViewModel
) : RxActionsExecutor<MainState, MainViewModel>(mainViewModel), MainActions