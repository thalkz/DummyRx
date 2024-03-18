package operators

import Observable
import Observer

class ObservableCombineLatest<T, U, V>(
    private val observableA: Observable<T>,
    private val observableB: Observable<U>,
    private val mapper: (T, U)-> V,
): Observable<V>() {

    private var latestA: T? = null
    private var latestB: U? = null
    override fun subscribeActual(observer: Observer<V>) {
        observableA.subscribe { currentA ->
            latestA = currentA
            val currentB = latestB
            if (currentB != null) {
                observer.onNext(mapper(currentA, currentB))
            }
        }
        observableB.subscribe {currentB ->
            latestB = currentB
            val currentA = latestA
            if (currentA != null) {
                observer.onNext(mapper(currentA, currentB))
            }
        }
        // TODO When to call on onComplete() ???
    }
}