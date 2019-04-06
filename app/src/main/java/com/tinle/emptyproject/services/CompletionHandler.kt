package com.tinle.emptyproject.services

interface CompletionHandler<T, S> {
    fun onSuccess(result: T)
    fun onFailed(result: S)
}