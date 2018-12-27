package com.example.there.aroundmenow.di.fragment.visualizer

import androidx.lifecycle.ViewModelProviders
import com.example.there.aroundmenow.base.architecture.vm.ObservableStateHolder
import com.example.there.aroundmenow.di.scope.ChildFragmentScope
import com.example.there.aroundmenow.di.vm.ViewModelFactory
import com.example.there.aroundmenow.visualizer.camera.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CameraModule {

    @ChildFragmentScope
    @Binds
    abstract fun cameraActions(actionsExecutor: CameraActionsExecutor): CameraActions

    @Binds
    abstract fun cameraViewObservableState(viewModel: CameraViewModel): ObservableStateHolder<CameraState>

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun cameraViewModel(
            factory: ViewModelFactory,
            fragment: CameraFragment
        ): CameraViewModel = ViewModelProviders.of(fragment, factory).get(CameraViewModel::class.java)
    }
}