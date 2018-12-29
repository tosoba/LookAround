package com.example.there.aroundmenow.util.ext

import androidx.fragment.app.Fragment
import com.example.there.aroundmenow.main.MainActivity

val Fragment.mainActivity: MainActivity?
    get() = activity as? MainActivity

val Fragment.orientation: Int
    get() = resources.configuration.orientation