package com.fappslab.libraries.arch.extension

import com.fappslab.libraries.arch.network.exception.extension.toThrowable
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.schedulerOn(scheduler: Scheduler): Single<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(scheduler)
}

fun <T> Maybe<T>.schedulerOn(scheduler: Scheduler): Maybe<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(scheduler)
}

fun <T> Flowable<T>.schedulerOn(schedulers: Scheduler): Flowable<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(schedulers)
}

fun Completable.schedulerOn(schedulers: Scheduler): Completable {
    return subscribeOn(Schedulers.io())
        .observeOn(schedulers)
}

fun <T> Single<T>.parseHttpError(): Single<T> {
    return onErrorResumeNext { throwable ->
        Single.error(throwable.toThrowable())
    }
}

fun Completable.parseHttpError(): Completable {
    return onErrorResumeNext { throwable ->
        Completable.error(throwable.toThrowable())
    }
}
