package com.example.there.aroundmenow.di.fragment.placedetails

import androidx.lifecycle.ViewModelProviders
import com.example.there.aroundmenow.base.architecture.vm.ObservableStateHolder
import com.example.there.aroundmenow.di.scope.ChildFragmentScope
import com.example.there.aroundmenow.di.vm.ViewModelFactory
import com.example.there.aroundmenow.placedetails.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class PlaceDetailsModule {

    @ChildFragmentScope
    @Binds
    abstract fun placeDetailsActions(actionsExecutor: PlaceDetailsActionsExecutor): PlaceDetailsActions

    @Binds
    abstract fun placeDetailsObservableState(viewModel: PlaceDetailsViewModel): ObservableStateHolder<PlaceDetailsState>

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun placeDetailsViewModel(
            factory: ViewModelFactory,
            fragment: PlaceDetailsFragment
        ): PlaceDetailsViewModel = ViewModelProviders.of(fragment, factory).get(PlaceDetailsViewModel::class.java)
    }
}