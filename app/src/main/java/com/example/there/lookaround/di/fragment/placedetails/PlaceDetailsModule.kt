package com.example.there.lookaround.di.fragment.placedetails

import androidx.lifecycle.ViewModelProviders
import com.example.there.lookaround.base.architecture.vm.ObservableStateHolder
import com.example.there.lookaround.di.scope.FragmentScope
import com.example.there.lookaround.di.vm.ViewModelFactory
import com.example.there.lookaround.placedetails.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class PlaceDetailsModule {

    @FragmentScope
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