package com.example.there.aroundmenow.main

import com.example.there.aroundmenow.base.architecture.executor.RxActionsExecutor
import javax.inject.Inject

class MainActionsExecutor @Inject constructor(
    mainViewModel: MainViewModel
) : RxActionsExecutor.HostUnaware<MainState, MainViewModel>(mainViewModel), MainActions