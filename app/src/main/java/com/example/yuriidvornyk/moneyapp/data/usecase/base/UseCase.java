package com.example.yuriidvornyk.moneyapp.data.usecase.base;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by yurii.dvornyk on 2017-11-23.
 */

public abstract class UseCase<T> {

    private static final String TAG = UseCase.class.getSimpleName();

    private Scheduler callerThread;
    private Scheduler resultThread;
    private CompositeDisposable disposables;

    public UseCase(Scheduler callerThread, Scheduler resultThread) {
        this.callerThread = callerThread;
        this.resultThread = resultThread;
        disposables = new CompositeDisposable();
    }

    public Flowable<T> getRawFlowable() {
        return buildUseCaseFlowable();
    }

    public void execute(Consumer<? super T> onNext) {
        disposables.add(decorateWithTreads().subscribe(onNext));
    }

    public void execute(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {
        disposables.add(decorateWithTreads().subscribe(onNext, onError));
    }

    public void execute(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete) {
        disposables.add(decorateWithTreads().subscribe(onNext, onError, onComplete));
    }

    public void execute(Action onComplete) {
        execute(onNext -> {}, onError -> {}, onComplete);
    }

    public void unsubscribe() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    protected abstract Flowable<T> buildUseCaseFlowable();

    private Flowable<T> decorateWithTreads() {
        return buildUseCaseFlowable()
                .subscribeOn(callerThread)
                .observeOn(resultThread);
    }
}
