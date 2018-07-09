package com.framgia.demo.jdk9;

import java.util.stream.Stream;

public class StreamApiNewMethodsDemo {

    public static void main(String[] args) {
        //takeWhile();
        //dropWhile();
        ofNullable();
    }

    private static void takeWhile() {
        Stream.of("a", "b", "c", "", "e")
                .takeWhile(s -> !s.isEmpty())
                .forEach(System.out::print);
    }

    private static void dropWhile() {
        Stream.of("a", "b", "c", "de", "f")
                .dropWhile(s -> s.length() <= 1)
                .forEach(System.out::print);
    }

    private static void ofNullable(){
        System.out.println(Stream.ofNullable("42").count());
        System.out.println(Stream.ofNullable(null).count());
    }

}
