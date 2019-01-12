package com.example.there.lookaround.util.ext

import androidx.fragment.app.Fragment
import com.example.there.lookaround.main.MainActivity

val Fragment.mainActivity: MainActivity?
    get() = activity as? MainActivity

val Fragment.orientation: Int
    get() = resources.configuration.orientation