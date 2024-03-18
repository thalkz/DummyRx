package operators

import Observable
import Observer

class ObservableFromList<T>(
   private val values: List<T>
): Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        for (value in values) {
            observer.onNext(value)
        }
        observer.onComplete()
    }
}