package com.example.there.aroundmenow.util.ext

import androidx.viewpager.widget.ViewPager
import com.jakewharton.rxbinding2.support.v4.view.RxViewPager
import io.reactivex.Observable

val ViewPager.pageSelected: Observable<Int>
    get() = RxViewPager.pageSelections(this).skipInitialValue()