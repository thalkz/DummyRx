import operators.*
import schedulers.ExecutorScheduler
import java.util.concurrent.Executors

open class Observable<T> {

    open fun subscribeActual(observer: Observer<T>) {}

    fun subscribe(observer: Observer<T>) {
        subscribeActual(observer)
    }

    fun subscribe(onNext: (T) -> Unit) {
        subscribe(object : Observer<T> {
            override fun onNext(value: T) {
                onNext(value)
            }
        })
    }

    fun doOnEach(onNext: (T) -> Unit = {}, onComplete: () -> Unit = {}, onSubscribe: () -> Unit = {}): Observable<T> {
        return ObservableDoOnEach(this, onNext, onComplete, onSubscribe)
    }

    fun <U>map(mapper: (T) -> U): Observable<U> {
        return ObservableMap(this, mapper)
    }

    fun take(count: Int): Observable<T> {
        return ObservableTake(this, count)
    }

    fun <U>flatMap(mapper: (T) -> Observable<U>): Observable<U> {
        return ObservableFlatMap(this, mapper)
    }

    fun delay(ms: Long): Observable<T> {
        return ObservableDelay(this, ms, scheduler = Schedulers.from(Executors.newSingleThreadExecutor()))
    }

    fun subscribeOn(scheduler: Scheduler): Observable<T> {
        return ObservableSubscribeOn(this, scheduler)
    }

    fun observeOn(scheduler: Scheduler): Observable<T> {
        return ObservableObserveOn(this, scheduler)
    }

    companion object {
        fun <T>just(value: T): Observable<T> {
            return ObservableFromValue(value)
        }

        fun <T>fromList(values: List<T>): Observable<T> {
            return ObservableFromList(values)
        }

        fun <T, U, V>combineLatest(a: Observable<T>, b: Observable<U>, mapper: (T, U) -> V): Observable<V> {
            return ObservableCombineLatest(a, b, mapper)
        }
    }
}