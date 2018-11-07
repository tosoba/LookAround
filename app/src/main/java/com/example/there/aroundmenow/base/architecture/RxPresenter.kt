package com.example.there.aroundmenow.base.architecture

import com.example.domain.task.base.ObservableTask
import com.example.domain.task.base.ObservableTaskWithInput
import com.example.there.aroundmenow.util.ext.disposeWith
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers

abstract class RxPresenter<State, VM : RxViewModel<State>> {

    lateinit var viewModel: VM

    protected fun mutate(mapCurrentStateToNextState: (State) -> State) {
        Observable.just(mapCurrentStateToNextState)
            .observeOn(AndroidSchedulers.mainThread()) // ensures mutations happen serially on main thread
            .zipWith(viewModel.state)
            .map { (reducer, state) -> reducer(state) }
            .subscribe(viewModel.state)
            .disposeWith(viewModel.disposables)
    }

    protected fun <EventArgs, TaskInput, TaskReturn> ObservableTaskWithInput<TaskInput, TaskReturn>.executeWithEventArgs(
        eventArgs: EventArgs,
        mapEventArgsToInput: (EventArgs) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Observable<TaskReturn> = Observable.just(eventArgs)
        .subscribeOn(scheduler)
        .mapToInputAndExecuteTask(this, mapEventArgsToInput)

    protected fun <EventArgs, TaskInput, TaskReturn> ObservableTaskWithInput<TaskInput, TaskReturn>.cancelUnfinishedAndExecuteEventArgs(
        disposable: Disposable,
        eventArgs: EventArgs,
        mapEventArgsToInput: (EventArgs) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Observable<TaskReturn> {
        viewModel.disposables.remove(disposable)
        return executeWithEventArgs(eventArgs, mapEventArgsToInput, scheduler)
    }

    protected fun <TaskInput, TaskReturn> ObservableTaskWithInput<TaskInput, TaskReturn>.cancelUnfinishedAndExecuteWithState(
        disposable: Disposable,
        mapStateToParams: (State) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Observable<TaskReturn> {
        viewModel.disposables.remove(disposable)
        return executeWithState(mapStateToParams, scheduler)
    }

    protected fun <TaskInput, TaskReturn> ObservableTaskWithInput<TaskInput, TaskReturn>.executeWithState(
        mapStateToParams: (State) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Observable<TaskReturn> = Observable.just(viewModel.state.value)
        .subscribeOn(scheduler)
        .mapToInputAndExecuteTask(this, mapStateToParams)

    private fun <T, TaskInput, TaskReturn> Observable<T>.mapToInputAndExecuteTask(
        task: ObservableTaskWithInput<TaskInput, TaskReturn>,
        mapper: (T) -> TaskInput
    ): Observable<TaskReturn> = map(mapper).flatMap { task.execute(it) }

    protected fun <TaskReturn> ObservableTask<TaskReturn>.cancelUnfinishedAndExecute(
        disposable: Disposable,
        scheduler: Scheduler = Schedulers.io()
    ): Observable<TaskReturn> {
        viewModel.disposables.remove(disposable)
        return execute(scheduler)
    }

    protected fun <TaskReturn> ObservableTask<TaskReturn>.execute(
        scheduler: Scheduler = Schedulers.io()
    ): Observable<TaskReturn> = execute().subscribeOn(scheduler)

    protected fun <TaskReturn> Observable<TaskReturn>.mapToStateThenSubscribeAndDisposeWithViewModel(
        mapTaskReturnToState: (State, TaskReturn) -> State
    ) = observeOn(AndroidSchedulers.mainThread())
        .zipWith(viewModel.state)
        .map { (ret, state) -> mapTaskReturnToState(state, ret) }
        .subscribe(viewModel.state)
        .disposeWith(viewModel.disposables)
}