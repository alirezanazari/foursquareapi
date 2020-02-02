package ir.alirezanazari.domain.intractor

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver

abstract class UseCaseSingle<T, P>(
    private val io: Scheduler, private val ui: Scheduler
) : UseCase() {

    abstract fun build(param: P): Single<T>

    fun execute(observer: DisposableSingleObserver<T>, param: P) {

        val observable: Single<T> = build(param)
            .subscribeOn(io)
            .observeOn(ui)

        addDisposable(observable.subscribeWith(observer))
    }

}

abstract class UseCase{

    private var disposable: CompositeDisposable = CompositeDisposable()

    fun addDisposable(dispos: Disposable) = disposable.add(dispos)

    fun clearDisposable() {
        if (!disposable.isDisposed) disposable.dispose()
    }

    fun getDisposable(): CompositeDisposable{
        return disposable
    }

}