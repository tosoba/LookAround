package com.example.there.aroundmenow

import android.app.Activity
import android.app.Application
import com.example.there.aroundmenow.di.AppInjector
import com.pacoworks.rxpaper2.RxPaperBook
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class AroundMeNowApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
        RxPaperBook.init(this)
    }
}