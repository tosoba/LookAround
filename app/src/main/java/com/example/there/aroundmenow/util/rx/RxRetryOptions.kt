package com.example.there.aroundmenow.util.rx

sealed class RxRetryOptions {
    sealed class Retry(val times: Long) : RxRetryOptions() {
        class Instantly(times: Long) : Retry(times)
        class AfterDelay(times: Long, val delay: RxDelay) : Retry(times)
    }

    object NoRetry : RxRetryOptions()
}