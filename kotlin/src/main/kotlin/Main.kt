import java.util.concurrent.Executors

fun main(args: Array<String>) {

    Observable
        .combineLatest(
            Observable
                .fromList(listOf("1", "2", "3"))
                .delay(1000),
            Observable
                .fromList(listOf("a", "b", "c"))
                .delay(1500),
        ) { left, right ->
            "$left-$right"
        }
        .observeOn(scheduler = Schedulers.from(Executors.newSingleThreadExecutor()))
        .doOnEach(
            onComplete = {
                println("on complete called")
            }
        )
        .subscribe {
            println("$it on ${Thread.currentThread().name}")
        }

/*
    Observable
        .fromList(listOf("one", "two", "three", "four"))
        .take(3)
        .flatMap {
            Observable
                .fromList(listOf("$it-a", "$it-b", "$it-c"))
                .delay(1000)
        }
        .subscribe {
            println("Result: $it")
        }
    */

/*    Observable
        .fromList(listOf("corentin", "antoine", "machin"))
        .doOnEach(onNext = {
            println("Got value on ${Thread.currentThread().name}")
        })
        .take(2)
        .map { str -> str.uppercase() }
        .doOnEach(onSubscribe = {
            println("Then susbcribed on ${Thread.currentThread().name}")
        })
        .subscribeOn(Schedulers.from(Executors.newFixedThreadPool(12)))
        .observeOn(Schedulers.from(Executors.newSingleThreadExecutor()))
        .doOnEach(onNext = {
            println("Then Got value on ${Thread.currentThread().name}")
        })
        .doOnEach(onSubscribe = {
            println("First susbcribed on ${Thread.currentThread().name}")
        })
        .subscribe(object: Observer<String> {
            override fun onNext(value: String) {
                println(Thread.currentThread().name)
                println(value)
            }

            override fun onComplete() {
                println("completed")
            }
        })*/
}