package com.example.there.lookaround.visualizer

import com.example.there.lookaround.base.architecture.vm.RxViewModel
import javax.inject.Inject

class VisualizerViewModel @Inject constructor() : RxViewModel<VisualizerState>(VisualizerState.INITIAL)