package com.example.there.aroundmenow.base.architecture

import com.example.domain.task.base.*
import com.example.there.aroundmenow.util.ext.disposeWith
import io.reactivex.*
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

    protected fun <EventArgs, TaskInput, TaskReturn> SingleTaskWithInput<TaskInput, TaskReturn>.executeWithEventArgs(
        eventArgs: EventArgs,
        mapEventArgsToInput: (EventArgs) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Single<TaskReturn> = Single.just(eventArgs)
        .subscribeOn(scheduler)
        .mapToInputAndExecuteTask(this, mapEventArgsToInput)

    protected fun <EventArgs, TaskInput, TaskReturn> FlowableTaskWithInput<TaskInput, TaskReturn>.executeWithEventArgs(
        eventArgs: EventArgs,
        mapEventArgsToInput: (EventArgs) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Flowable<TaskReturn> = Flowable.just(eventArgs)
        .subscribeOn(scheduler)
        .mapToInputAndExecuteTask(this, mapEventArgsToInput)

    protected fun <EventArgs, TaskInput> CompletableTaskWithInput<TaskInput>.executeWithEventArgs(
        eventArgs: EventArgs,
        mapEventArgsToInput: (EventArgs) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Completable = Single.just(eventArgs)
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

    protected fun <EventArgs, TaskInput, TaskReturn> SingleTaskWithInput<TaskInput, TaskReturn>.cancelUnfinishedAndExecuteEventArgs(
        disposable: Disposable,
        eventArgs: EventArgs,
        mapEventArgsToInput: (EventArgs) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Single<TaskReturn> {
        viewModel.disposables.remove(disposable)
        return executeWithEventArgs(eventArgs, mapEventArgsToInput, scheduler)
    }

    protected fun <TaskInput, TaskReturn> SingleTaskWithInput<TaskInput, TaskReturn>.cancelUnfinishedAndExecuteWithState(
        disposable: Disposable,
        mapStateToParams: (State) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Single<TaskReturn> {
        viewModel.disposables.remove(disposable)
        return executeWithState(mapStateToParams, scheduler)
    }

    protected fun <EventArgs, TaskInput, TaskReturn> FlowableTaskWithInput<TaskInput, TaskReturn>.cancelUnfinishedAndExecuteEventArgs(
        disposable: Disposable,
        eventArgs: EventArgs,
        mapEventArgsToInput: (EventArgs) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Flowable<TaskReturn> {
        viewModel.disposables.remove(disposable)
        return executeWithEventArgs(eventArgs, mapEventArgsToInput, scheduler)
    }

    protected fun <TaskInput, TaskReturn> FlowableTaskWithInput<TaskInput, TaskReturn>.cancelUnfinishedAndExecuteWithState(
        disposable: Disposable,
        mapStateToParams: (State) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Flowable<TaskReturn> {
        viewModel.disposables.remove(disposable)
        return executeWithState(mapStateToParams, scheduler)
    }

    protected fun <EventArgs, TaskInput> CompletableTaskWithInput<TaskInput>.cancelUnfinishedAndExecuteEventArgs(
        disposable: Disposable,
        eventArgs: EventArgs,
        mapEventArgsToInput: (EventArgs) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Completable {
        viewModel.disposables.remove(disposable)
        return executeWithEventArgs(eventArgs, mapEventArgsToInput, scheduler)
    }

    protected fun <TaskInput> CompletableTaskWithInput<TaskInput>.cancelUnfinishedAndExecuteWithState(
        disposable: Disposable,
        mapStateToParams: (State) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Completable {
        viewModel.disposables.remove(disposable)
        return executeWithState(mapStateToParams, scheduler)
    }

    protected fun <TaskInput, TaskReturn> ObservableTaskWithInput<TaskInput, TaskReturn>.executeWithState(
        mapStateToParams: (State) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Observable<TaskReturn> = Observable.just(viewModel.state.value)
        .subscribeOn(scheduler)
        .mapToInputAndExecuteTask(this, mapStateToParams)

    protected fun <TaskInput, TaskReturn> SingleTaskWithInput<TaskInput, TaskReturn>.executeWithState(
        mapStateToParams: (State) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Single<TaskReturn> = Single.just(viewModel.state.value)
        .subscribeOn(scheduler)
        .mapToInputAndExecuteTask(this, mapStateToParams)

    protected fun <TaskInput, TaskReturn> FlowableTaskWithInput<TaskInput, TaskReturn>.executeWithState(
        mapStateToParams: (State) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Flowable<TaskReturn> = Flowable.just(viewModel.state.value)
        .subscribeOn(scheduler)
        .mapToInputAndExecuteTask(this, mapStateToParams)

    protected fun <TaskInput> CompletableTaskWithInput<TaskInput>.executeWithState(
        mapStateToParams: (State) -> TaskInput,
        scheduler: Scheduler = Schedulers.io()
    ): Completable = Single.just(viewModel.state.value)
        .subscribeOn(scheduler)
        .mapToInputAndExecuteTask(this, mapStateToParams)

    private fun <T, TaskInput, TaskReturn> Observable<T>.mapToInputAndExecuteTask(
        task: ObservableTaskWithInput<TaskInput, TaskReturn>,
        mapper: (T) -> TaskInput
    ): Observable<TaskReturn> = map(mapper).flatMap { task.execute(it) }

    private fun <T, TaskInput, TaskReturn> Single<T>.mapToInputAndExecuteTask(
        task: SingleTaskWithInput<TaskInput, TaskReturn>,
        mapper: (T) -> TaskInput
    ): Single<TaskReturn> = map(mapper).flatMap { task.execute(it) }

    private fun <T, TaskInput, TaskReturn> Flowable<T>.mapToInputAndExecuteTask(
        task: FlowableTaskWithInput<TaskInput, TaskReturn>,
        mapper: (T) -> TaskInput
    ): Flowable<TaskReturn> = map(mapper).flatMap { task.execute(it) }

    private fun <T, TaskInput> Single<T>.mapToInputAndExecuteTask(
        task: CompletableTaskWithInput<TaskInput>,
        mapper: (T) -> TaskInput
    ): Completable = map(mapper).flatMapCompletable { task.execute(it) }

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

    protected fun <TaskReturn> SingleTask<TaskReturn>.execute(
        scheduler: Scheduler = Schedulers.io()
    ): Single<TaskReturn> = execute().subscribeOn(scheduler)

    protected fun <TaskReturn> FlowableTask<TaskReturn>.execute(
        scheduler: Scheduler = Schedulers.io()
    ): Flowable<TaskReturn> = execute().subscribeOn(scheduler)

    protected fun CompletableTask.execute(
        scheduler: Scheduler = Schedulers.io()
    ): Completable = execute().subscribeOn(scheduler)

    protected fun <TaskReturn> Observable<TaskReturn>.mapToStateThenSubscribeAndDisposeWithViewModel(
        mapTaskReturnToState: (State, TaskReturn) -> State
    ) = observeOn(AndroidSchedulers.mainThread())
        .zipWith(viewModel.state)
        .map { (ret, state) -> mapTaskReturnToState(state, ret) }
        .subscribe(viewModel.state)
        .disposeWith(viewModel.disposables)

    protected fun <TaskReturn> Single<TaskReturn>.mapToStateThenSubscribeAndDisposeWithViewModel(
        mapTaskReturnToState: (State, TaskReturn) -> State
    ) = observeOn(AndroidSchedulers.mainThread())
        .zipWith(viewModel.state.single(viewModel.initialState))
        .map { (ret, state) -> mapTaskReturnToState(state, ret) }
        .subscribe(viewModel.state)
        .disposeWith(viewModel.disposables)

    protected fun <TaskReturn> Flowable<TaskReturn>.mapToStateThenSubscribeAndDisposeWithViewModel(
        mapTaskReturnToState: (State, TaskReturn) -> State,
        backpressureStrategy: BackpressureStrategy
    ) = observeOn(AndroidSchedulers.mainThread())
        .zipWith(viewModel.state.toFlowable(backpressureStrategy))
        .map { (ret, state) -> mapTaskReturnToState(state, ret) }
        .subscribe(viewModel.state)
        .disposeWith(viewModel.disposables)

    protected fun Completable.subscribeAndDisposeWithViewModel() = observeOn(AndroidSchedulers.mainThread())
        .subscribe()
        .disposeWith(viewModel.disposables)
}