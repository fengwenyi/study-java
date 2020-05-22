package com.fengwenyi.study_reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * @author Erwin Feng
 * @since 2020/5/21
 */
public class ReactorTests {

    String [] names = {"Sophia", "Jackson"};

    @Test
    public void flux() {

        // 创建方式1
        Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4, 5);

        Flux<String> stringFlux = Flux.just(names);

        List<String> list = Arrays.asList(names);

        // 创建方式2
        Flux<String> listFlux = Flux.fromIterable(list);

        // 创建方式3
        Flux<Integer> rangeFlux = Flux.range(1, 5);

        // 这里有问题？？？？
        // 创建方式4
        Flux<Long> longFlux = Flux.interval(Duration.ofMillis(1000));

        // 创建方式5
        // 从一个flux生成一个新的flux
        Flux<String> stringFlux2 = Flux.from(stringFlux);

        // 订阅之后才会生效
        integerFlux.subscribe(System.out::println);
    }

    @Test
    public void mono() {
        // 创建方式1
        Mono<String> stringMono = Mono.just("Hello World");
        // 创建方式2
        Mono<String> stringMono2 = Mono.fromCallable(() -> "Hello World");
        // 创建方式3
        Mono<String> stringMono3 = Mono.fromFuture(CompletableFuture.completedFuture("Hello World"));

        Random random = new Random();
        // 创建方式4
        Mono<Double> doubleMono = Mono.fromSupplier(random::nextDouble);
        // 创建方式5
        Mono<Double> doubleMono2 = Mono.from(doubleMono);
        // 创建方式6
        Mono<Integer> integerMono = Mono.from(Flux.range(1, 5)); // 取第一个 1
        integerMono.subscribe(System.out::println); // 1
    }

}
