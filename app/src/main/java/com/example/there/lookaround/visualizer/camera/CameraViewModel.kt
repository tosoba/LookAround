package com.example.there.lookaround.visualizer.camera

import com.example.there.lookaround.base.architecture.vm.RxViewModel
import javax.inject.Inject

class CameraViewModel @Inject constructor() : RxViewModel<CameraState>(CameraState.INITIAL)