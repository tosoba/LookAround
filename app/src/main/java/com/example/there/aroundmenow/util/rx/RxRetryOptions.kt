package com.example.there.aroundmenow.util.rx

sealed class RxRetryOptions {
    class RetryInstantly(val times: Long) : RxRetryOptions()
    class RetryAfterDelay(val delay: RxDelay) : RxRetryOptions()
    object NoRetry : RxRetryOptions()
}