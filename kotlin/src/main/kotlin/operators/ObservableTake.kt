package operators

import Observable
import Observer

class ObservableTake<T>(
    private val source: Observable<T>,
    private var count: Int,
) :  Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        source.subscribe(ObserverMap(observer))
    }

    inner class ObserverMap(private val observer: Observer<T>): Observer<T> {
        override fun onNext(value: T) {
            if (count > 0) {
                observer.onNext(value)
            } else if (count == 0) {
                observer.onComplete()
            }
            count--
        }

        override fun onComplete() {
            observer.onComplete()
        }
    }
}