package operators

import Observable
import Observer
import Scheduler
import schedulers.ExecutorScheduler
import java.util.concurrent.Executors
import java.util.concurrent.locks.Lock

class ObservableThrottle<T>(
    private val source: Observable<T>,
    private var ms: Long,
    private var scheduler: Scheduler,
) :  Observable<T>() {

    override fun subscribeActual(observer: Observer<T>) {
        source.subscribe(ObserverThrottle(observer))
    }

    inner class ObserverThrottle(private val observer: Observer<T>): Observer<T> {
        override fun onNext(value: T) {
            scheduler.schedule {
                Thread.sleep(ms)
                observer.onNext(value)
            }
        }

        override fun onComplete() {
            observer.onComplete()
        }
    }
}