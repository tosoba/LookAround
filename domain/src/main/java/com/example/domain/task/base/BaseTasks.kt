package com.example.domain.task.base

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface ObservableTask<Ret> {
    fun execute(): Observable<Ret>
}

interface ObservableTaskWithInput<Input, Ret> {
    fun execute(input: Input): Observable<Ret>
}

interface SingleTask<Ret> {
    fun execute(): Single<Ret>
}

interface SingleTaskWithInput<Input, Ret> {
    fun execute(input: Input): Single<Ret>
}

interface FlowableTask<Ret> {
    fun execute(): Flowable<Ret>
}

interface FlowableTaskWithInput<Input, Ret> {
    fun execute(input: Input): Flowable<Ret>
}

interface CompletableTask {
    fun execute(): Completable
}

interface CompletableTaskWithInput<Input> {
    fun execute(input: Input): Completable
}
