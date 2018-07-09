package com.framgia.demo.jdk9;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class StreamApiCollectorsDemo {

    public static void main(String[] args) {
        //filtering();
        flatMapping();
    }

    private static void filtering() {
        List<Integer> numbers = List.of(1, 2, 3, 5, 5);

        println(numbers.stream()
                .filter(val -> val > 3)
                .collect(Collectors.groupingBy(i -> i, Collectors.counting())));

        println(numbers.stream()
                .collect(Collectors.groupingBy(i -> i,
                        Collectors.filtering(val -> val > 3, Collectors.counting()))));
    }

    private static void flatMapping() {
        Blog blog1 = new Blog("1", Arrays.asList("Nice", "Very Nice"));
        Blog blog2 = new Blog("2", Arrays.asList("Disappointing", "Ok", "Could be better"));

        //Java 8
        Map<String, List<List<String>>> authorComments1 = List.of(blog1, blog2).stream()
                .collect(Collectors.groupingBy(Blog::getAuthorName,
                        Collectors.mapping(Blog::getComments, Collectors.toList())));

        System.out.println(authorComments1.size());
        System.out.println(authorComments1.get("1").get(0).size());
        System.out.println(authorComments1.get("2").get(0).size());

        //Java 9
        Map<String, List<String>> authorComments2 = List.of(blog1, blog2).stream()
                .collect(Collectors.groupingBy(Blog::getAuthorName,
                        Collectors.flatMapping(blog -> blog.getComments().stream(),
                                Collectors.toList())));

        System.out.println(authorComments2.size());
        System.out.println(authorComments2.get("1").size());
        System.out.println(authorComments2.get("2").size());
    }

    private static void println(Map<Integer, Long> result) {
        System.out.println("SIZE --> " + result.size());
        for (Map.Entry<Integer, Long> entry : result.entrySet()) {
            System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
        }
    }

    static class Blog {
        public String getAuthorName() {
            return authorName;
        }

        public List<String> getComments() {
            return comments;
        }

        private String authorName;
        private List<String> comments;

        Blog(String authorName, List<String> comments) {
            this.authorName = authorName;
            this.comments = comments;
        }
    }

}
