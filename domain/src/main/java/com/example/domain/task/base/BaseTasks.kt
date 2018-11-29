package com.example.domain.task.base

interface Task<Result> {
    val result: Result
}

interface TaskWithInput<Input, Result> {
    fun executeWith(input: Input): Result
}