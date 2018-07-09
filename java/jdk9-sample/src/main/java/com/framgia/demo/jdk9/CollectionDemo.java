package com.framgia.demo.jdk9;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Map.entry;

public class CollectionDemo {

    public static void main(String[] args) {
        list();
        //map();
    }

    private static void list() {
        List<String> ninjaTurtles = List.of(
                "Leonardo",
                "Donatello",
                "Raphael",
                "Michelangelo");

        try {
            ninjaTurtles.add("New suspicious turtle");
        } catch (UnsupportedOperationException e) {
            System.out.println("Can not add a new item to ImmutableCollections.List");
        }

        try {
            ninjaTurtles.set(Objects.checkIndex(3, ninjaTurtles.size()), "New suspicious turtle");
        } catch (UnsupportedOperationException e) {
            System.out.println("Can set new value to ImmutableCollections.List");
        }

        System.out.println("Ninja turtles: " + ninjaTurtles);
    }

    private static void map() {
        Map<String, Integer> correctTurtles= Map.ofEntries(
                entry("Leonardo", 0),
                entry("Donatello", 1),
                entry("Raphael", 2),
                entry("Michelangelo", 3),
                entry("Unknown hero 1", 4),
                entry("Unknown hero 2", 5),
                entry("Unknown hero 3", 6),
                entry("Unknown hero 4", 7),
                entry("Unknown hero 5", 8),
                entry("Unknown hero 6", 9),
                entry("Unknown hero 7", 10),
                entry("Unknown hero 8", 11),
                entry("Unknown hero 9", 12)
        );
        System.out.println(correctTurtles);
    }
}
