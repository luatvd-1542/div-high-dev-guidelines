# Overview

AssertJ là một thư viện Java cung cấp tập hợp các assertions phong phú, các thông báo lỗi hữu ích, cải thiện khả năng đọc code đơn giản hơn...

# How to use

### Supported Java version

Phiên bản AssertJ core phụ thuộc vào Java version :
* AssertJ Core 3.x yêu cầu từ Java 8 trở lên
* AssertJ Core 2.x yêu cầu từ Java 7 trở lên

### Android Support

AssertJ không hỗ trợ chính thức cho Android nhưng hầu hết Android đều tương thích

* AssertJ Core 3.x tương thích với API 26+, ngoại trừ soft assertions và asumptions
* AsertJ Core 2.x tương thích với API 26+ , với API <26 ngoại trừ Path assertions.

~~~java
testCompile("org.assertj:assertj-core:3.13.2")
~~~
~~~java
testCompile("org.assertj:assertj-core:2.9.1")
~~~

### Basic Assertions
~~~java
assertThat(frodo.getName()).isEqualTo("Frodo");
assertThat(frodo).isNotEqualTo(sauron);
~~~

### Chaining string assertions
~~~java
assertThat(frodo.getName()).startsWith("Fro")
                           .endsWith("do")
                           .isEqualToIgnoringCase("frodo");
~~~

### Collections assertions
~~~java
// Supposed fellowshipOfTheRing is a List<TolkienCharacter>
assertThat(fellowshipOfTheRing).hasSize(9)
                               .contains(frodo, sam)
                               .doesNotContain(sauron);
~~~

### as() assertions

as() được sử dụng để mô tả test và sẽ show trước khi thông báo lỗi

~~~java
assertThat(frodo.getAge()).as("check %s's age", frodo.getName()).isEqualTo(33);
~~~

### Exception assertions

~~~java
assertThatThrownBy(() -> { throw new Exception("boom!"); }).hasMessage("boom!");
~~~

### Extracting assertions
Sử dụng tích năng extracting (giải nén) để kiểm tra các tên của list fellowshipOfTheRing :
~~~java
assertThat(fellowshipOfTheRing).extracting(TolkienCharacter::getName)
                               .doesNotContain("Sauron", "Elrond");
~~~
Để trích xuất nhiều giá trị cùng một lúc được nhóm trong một bộ dữ liệu :
~~~java
assertThat(fellowshipOfTheRing).extracting("name", "age", "race.name")
                               .contains(tuple("Boromir", 37, "Man"),
                                         tuple("Sam", 38, "Hobbit"),
                                         tuple("Legolas", 1000, "Elf"));
~~~

### Filter collections

Để lọc tập dữ liệu trước khi thực test :

~~~java
assertThat(fellowshipOfTheRing).filteredOn(character -> character.getName().contains("o"))
                               .containsOnly(aragorn, frodo, legolas, boromir);
~~~

### Combine filtering vs extraction

~~~java
assertThat(fellowshipOfTheRing).filteredOn(character -> character.getName().contains("o"))
                               .containsOnly(aragorn, frodo, legolas, boromir)
                               .extracting(character -> character.getRace().getName())
                               .contains("Hobbit", "Elf", "Man");
~~~

Ngoài những bộ assert trên, còn rất nhiều assertions nữa như :
* iterable
* stream
* array
* map
* dates
* path
* file
* numbers
* predicate
* options
* ...

Ref : https://assertj.github.io/doc/#assertj-core-assertions-guide



