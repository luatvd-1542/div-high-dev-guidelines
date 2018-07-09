package com.framgia.demo.jdk9;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class OptionalDemo {

    public static void main(String[] args) {
        demoOptionalOr();
        demoOptionalIfPresentOrElse();
        demoOptionalStream();
    }

    private static void demoOptionalOr() {
        String expectedHero = "Leonardo";
        Optional<String> value = Optional.of(expectedHero);
        Optional<String> defaultHero = Optional.of("Default hero");
        Optional<String> result = value.or(() -> defaultHero);
        System.out.println(result.get());
    }

    private static void demoOptionalIfPresentOrElse() {
        Optional<String> hero = Optional.of("Raphael");
        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger onEmptyOptionalCounter = new AtomicInteger(0);

        hero.ifPresentOrElse(
                v -> successCounter.incrementAndGet(),
                onEmptyOptionalCounter::incrementAndGet);

        System.out.println(successCounter.get());
        System.out.println(onEmptyOptionalCounter.get());
    }

    private static void demoOptionalStream() {
        Optional<String> hero = Optional.of("Donatello");
        List<String> heroes = hero.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(heroes);
    }
}
