package com.example.there.aroundmenow.util.rx

import io.reactivex.Observable

fun <T> Observable<T>.retryWithOptions(options: RxRetryOptions) = when (options) {
    is RxRetryOptions.NoRetry -> this
    is RxRetryOptions.RetryInstantly -> retry(options.times)
    is RxRetryOptions.RetryAfterDelay -> retryWhen {
        it.flatMap { Observable.timer(options.delay.delayTime, options.delay.timeUnit) }
    }
}