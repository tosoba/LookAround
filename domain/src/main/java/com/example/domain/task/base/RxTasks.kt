package com.example.domain.task.base

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface ObservableTask<Ret> : Task<Observable<Ret>>

interface ObservableTaskWithInput<Input, Ret> : TaskWithInput<Input, Observable<Ret>>

interface SingleTask<Ret> : Task<Single<Ret>>

interface SingleTaskWithInput<Input, Ret> : TaskWithInput<Input, Single<Ret>>

interface FlowableTask<Ret> : Task<Flowable<Ret>>

interface FlowableTaskWithInput<Input, Ret> : TaskWithInput<Input, Flowable<Ret>>

interface CompletableTask : Task<Completable>

interface CompletableTaskWithInput<Input> : TaskWithInput<Input, Completable>
