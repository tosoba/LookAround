package com.example.there.aroundmenow.visualizer.camera

import com.example.there.aroundmenow.base.architecture.vm.RxViewModel
import javax.inject.Inject

class CameraViewModel @Inject constructor() : RxViewModel<CameraState>(CameraState.INITIAL)