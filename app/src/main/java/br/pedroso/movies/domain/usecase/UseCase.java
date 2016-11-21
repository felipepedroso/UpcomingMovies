package br.pedroso.movies.domain.usecase;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public abstract class UseCase<T> {
    private Subscription subscription = Subscriptions.empty();
    private Scheduler schedulerExecutor;
    private Scheduler schedulerObserver;

    public UseCase(Scheduler schedulerExecutor, Scheduler schedulerObserver) {
        this.schedulerExecutor = schedulerExecutor;
        this.schedulerObserver = schedulerObserver;
    }

    protected abstract Observable<T> buildUseCaseObservable();

    public void execute(Subscriber<T> useCaseSubscriber) {
        this.subscription = buildUseCaseObservable()
                .subscribeOn(schedulerExecutor)
                .observeOn(schedulerObserver)
                .subscribe(useCaseSubscriber);
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
