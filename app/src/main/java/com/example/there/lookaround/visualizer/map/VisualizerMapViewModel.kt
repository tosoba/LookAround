package com.example.there.lookaround.visualizer.map

import com.example.there.lookaround.base.architecture.vm.RxViewModel
import javax.inject.Inject

class VisualizerMapViewModel @Inject constructor() : RxViewModel<VisualizerMapState>(VisualizerMapState.INITIAL)