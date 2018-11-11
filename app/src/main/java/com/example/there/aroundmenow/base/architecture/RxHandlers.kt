package com.example.there.aroundmenow.base.architecture

import android.util.Log
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

object RxHandlers {
    object Exception {
        val loggingConsumer = Consumer<Throwable> {
            Log.e("RxException", it.message ?: "Unknown error")
        }
    }

    object OnCompleteAction {
        val empty = Action {}
    }
}

