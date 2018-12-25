package com.example.there.aroundmenow.visualizer

import com.example.there.aroundmenow.base.architecture.vm.RxViewModel
import javax.inject.Inject

class VisualizerViewModel @Inject constructor() : RxViewModel<VisualizerState>(VisualizerState.INITIAL)