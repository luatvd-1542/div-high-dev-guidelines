# Junit 5
Junit là một trong những framework unit-testing phổ biến nhất dùng cho Java. Phiên bản Junit 5 có những cải tiến đáng kể giúp code unit test rõ ràng, dễ cài đặt và phù hợp vs nhiều mục đích test. Bên cạnh đó Junit 5 cũng tương thích vs Java 8, hỗ trợ test cho biểu thức lambda, method async await ...

## Maven Dependencies
- Junit 5 làm việc trên Java 8
- add Junit 5 dependency trong file pom.xml như sau:

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-engine</artifactId>
    <version>5.1.0</version>
    <scope>test</scope>
</dependency>
```

IntelliJ hỗ trợ JUnit 5 ở cài đặt mặc định, vì vậy để chạy JUnit 5 trên IntelliJ chúng ta chỉ cần click –> Run unit test.

Cần chi tiết hơn về cài đặt, bạn có thể đọc [ở đây](https://viblo.asia/p/nhung-dieu-co-ban-ve-junit-qzakzJwWkyO)

## Architecture

![](./images/junit_architecture.png)

- JUnit test framework cung cấp cho chúng ta các gói lớp có sẵn cho phép chúng ta viết các phương thức test một cách dễ dàng.
- TestRunner sẽ chạy các test và trả về kết quả là các Test Results.
- Các lớp của chương trình test chúng ta sẽ được kế thừa các lớp trừu tượng TestCase.
- Khi viết các Test Case chúng ta cần biết và hiểu lớp Assert class.
- Một số định nghĩa trong mô hình tổng quát:
    - Test case : test case định nghĩa môi trường mà nó có thể sử dụng để chạy nhiều test khác nhau
    - TestSuite : testsuite là chạy một tập các test case và nó cũng có thể bao gồm nhiều test suite khác, test suite chính là tổ hợp các test.

## 

##### Tại sao phải tạo 1 test suit?
- Hiển nhiên là bạn phải kiểm tra xem đoạn code của bạn có đúng hay không?
    -   Bạn có thể xây dựng một bộ kiểm thử bao gồm nhiều các test khác nhau có thể chạy trong bất cứ thời gian nào.
- Nhược điểm của việc viết 1 test suite:
    -   Đó là rất nhiều chương trình được lập trình thêm: "Đúng!", nhưng chúng ta có thể sử dụng những framework có sẵn giúp chúng ta giảm bớt trong quá trình lập trình.
    -   Có nhiều ý kiến cho rằng đó là công việc không cần thiết tốn thời gian , không có thời gian làm những công việc thêm đó: "Đó là ý kiến sai lầm!", Những thực nghiệm chỉ ra rằng những test suites sẽ giảm thời gian sửa lỗi nhiều hơn thời gian dành cho việc xây dựng những test suites đó.
- Lợi ích của việc viết 1 test suite.
    -   Làm giảm tổng số lỗi trong quá trình code.
    -   Làm cho dòng code của bạn trở lên dễ bảo trì và tái sử dụng hơn.
   


## Index
1. [unit ](./01-unit_test.md)
2. [Junit5](./02-junit5.md)
3. [Mockito](./03-Mockito.md)
4. [Spring Boot](./04-Spring_boot.md)