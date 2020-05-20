package com.fengwenyi.study_reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * @author Erwin Feng
 * @since 2020/5/21
 */
public class ReactorTests {

    @Test
    public void flux() {

        String [] names = {"Sophia", "Jackson"};

        Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4, 5);
        // 订阅之后才会生效
        integerFlux.subscribe(System.out::println);

        System.out.println("------------------------");

        Flux<String> stringFlux = Flux.just(names);
        stringFlux.subscribe(System.out::println);

        System.out.println("------------------------");

        List<String> list = Arrays.asList(names);
        Flux<String> listFlux = Flux.fromIterable(list);
        listFlux.subscribe(System.out::println);

        System.out.println("------------------------");

        Flux<Integer> rangeFlux = Flux.range(1, 5);
        rangeFlux.subscribe(System.out::println);

        System.out.println("------------------------");

        // 这里有问题？？？？
        Flux<Long> longFlux = Flux.interval(Duration.ofMillis(1000));
        longFlux.subscribe(System.out::println);

        System.out.println("------------------------");

        // 从一个flux生成一个新的flux
        Flux<String> stringFlux2 = Flux.from(stringFlux);
        stringFlux2.subscribe(System.out::println);
    }

}
