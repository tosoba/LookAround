package com.example.there.lookaround.di.fragment.places

import androidx.lifecycle.ViewModelProviders
import com.example.there.lookaround.base.architecture.vm.ObservableStateHolder
import com.example.there.lookaround.di.scope.ChildFragmentScope
import com.example.there.lookaround.di.vm.ViewModelFactory
import com.example.there.lookaround.places.favourites.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class FavouritesModule {

    @ChildFragmentScope
    @Binds
    abstract fun favouritesActions(actionsExecutor: FavouritesActionsExecutor): FavouritesActions

    @Binds
    abstract fun favouritesObservableState(viewModel: FavouritesViewModel): ObservableStateHolder<FavouritesState>

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun favouritesViewModel(
            factory: ViewModelFactory,
            fragment: FavouritesFragment
        ): FavouritesViewModel = ViewModelProviders.of(fragment, factory).get(FavouritesViewModel::class.java)
    }
}