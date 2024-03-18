package operators

import Observable
import Observer

class ObservableFlatMap<T, U>(
    private val source: Observable<T>,
    private val mapper: (T) -> Observable<U>
) :  Observable<U>() {

    override fun subscribeActual(observer: Observer<U>) {
        source.subscribe(ObserverFlatMap(observer))
    }

    inner class ObserverFlatMap(private val observer: Observer<U>): Observer<T> {
        override fun onNext(value: T) {
            mapper.invoke(value)
                .subscribe { observer.onNext(it) }
        }

        override fun onComplete() {
            observer.onComplete()
        }
    }
}