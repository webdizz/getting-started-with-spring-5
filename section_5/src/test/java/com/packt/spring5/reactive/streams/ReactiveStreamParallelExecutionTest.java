package com.packt.spring5.reactive.streams;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.stream.Stream;

public class ReactiveStreamParallelExecutionTest {

    @Test
    public void shouldStreamFromGeneratorInParallel() throws Exception {
        Flux.just("a", "b", "c")
                .concatWith(Flux.just("d", "b", "a"))
                .log()
                .map(String::toUpperCase)
                .parallel().runOn(Schedulers.parallel())
                .subscribe(next -> System.out.println(Thread.currentThread().getName() + " " + next));
    }

    @Test
    public void shouldBridgeWithBlockingWorld() throws Exception {
        Stream<Integer> integerStream = Flux.range(1, 20)
                .log()
                .map(item -> item * 2)
                .toStream();

        integerStream.forEach(System.out::println);
    }
}
