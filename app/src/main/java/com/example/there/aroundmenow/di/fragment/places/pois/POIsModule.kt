package com.example.there.aroundmenow.di.fragment.places.pois

import androidx.lifecycle.ViewModelProviders
import com.example.there.aroundmenow.di.vm.ViewModelFactory
import com.example.there.aroundmenow.places.pois.POIsActions
import com.example.there.aroundmenow.places.pois.POIsActionsExecutor
import com.example.there.aroundmenow.places.pois.POIsFragment
import com.example.there.aroundmenow.places.pois.POIsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class POIsModule {

    @POIsScope
    @Binds
    abstract fun poisPresenter(actionsExecutor: POIsActionsExecutor): POIsActions

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun poisViewModel(
            factory: ViewModelFactory,
            fragment: POIsFragment
        ): POIsViewModel = ViewModelProviders.of(fragment, factory).get(POIsViewModel::class.java)
    }
}