package com.example.there.lookaround.util.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer

fun <T> Observable<T>.retryWith(options: RxRetryOptions): Observable<T> = when (options) {
    is RxRetryOptions.NoRetry -> this
    is RxRetryOptions.Retry.Instantly -> retry(options.times)
    is RxRetryOptions.Retry.AfterDelay -> retryWhen {
        it.zipWith(
            Observable.range(1, options.times.toInt()),
            BiFunction<Throwable, Int, Int> { _, attempt -> attempt }
        ).flatMap { Observable.timer(options.delay.time, options.delay.unit) }
    }
}

fun <T> Single<T>.retryWith(options: RxRetryOptions): Single<T> = when (options) {
    is RxRetryOptions.NoRetry -> this
    is RxRetryOptions.Retry.Instantly -> retry(options.times)
    is RxRetryOptions.Retry.AfterDelay -> retryWhen {
        it.zipWith(
            Flowable.range(1, options.times.toInt()),
            BiFunction<Throwable, Int, Int> { _, attempt -> attempt }
        ).flatMap { Flowable.timer(options.delay.time, options.delay.unit) }
    }
}

fun <T> Flowable<T>.retryWith(options: RxRetryOptions): Flowable<T> = when (options) {
    is RxRetryOptions.NoRetry -> this
    is RxRetryOptions.Retry.Instantly -> retry(options.times)
    is RxRetryOptions.Retry.AfterDelay -> retryWhen {
        it.zipWith(
            Flowable.range(1, options.times.toInt()),
            BiFunction<Throwable, Int, Int> { _, attempt -> attempt }
        ).flatMap { Flowable.timer(options.delay.time, options.delay.unit) }
    }
}

fun Completable.retryWith(options: RxRetryOptions): Completable = when (options) {
    is RxRetryOptions.NoRetry -> this
    is RxRetryOptions.Retry.Instantly -> retry(options.times)
    is RxRetryOptions.Retry.AfterDelay -> retryWhen {
        it.zipWith(
            Flowable.range(1, options.times.toInt()),
            BiFunction<Throwable, Int, Int> { _, attempt -> attempt }
        ).flatMap { Flowable.timer(options.delay.time, options.delay.unit) }
    }
}

fun Completable.doOnErrorAndComplete(
    consumer: Consumer<Throwable>
): Completable = doOnError(consumer)
    .onErrorComplete()