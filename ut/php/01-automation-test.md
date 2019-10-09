# Automation Tests là gì?
- Kiểm thử tự động
- Viết code để test code (Test case as code)

![](./images/unit-test.png)

# Tại sao thực hiện Automation Test? (Lợi ích)
- Cân nhắc kỹ hơn khi viết code (design code)
- Tự tin hơn trong việc tích hợp, bảo trì, mở rộng và refactor hệ thống (Refactor with confidence)
- Tích hợp CI/CD tự động hóa quá trình merge, deploy
- Team work: teammate có thể không biết code của người khác

![](./images/automation-testing-benefit.png)

# Các loại Automation Test? (Test level)
Unit testing, Integration test, Funtional test, Feature test, Acceptance test, End-to-end test, System test...??

![](./images/test-pyramid.png)

Tuy nhiên developer trước tiên chỉ cần focus vào 2 loại test:
## Unit test
+ Test từng function hoặc method của một class
+ Không thực hiện những việc sau:
  * Truy vấn cơ sở dữ liệu (làm chậm quá trình test)
  * Sử dụng network, gọi api bên ngoài (làm chậm, kết quả không ổn định vì phụ thuộc vào mạng)
  * Sử dụng file system (làm chậm quá trình test)

![](./images/unit-test-1.png)

## Integration test:
+ Test việc kết hợp giữa các unit (function, method) với nhau => test một nhóm Unit

  Chẳng hạn Unit test, test từng method của Service và Controller sử dụng service

    * => Integration sẽ test việc sử dụng kết hợp service và controller

    * => Test route
+ Có thể truy vấn cơ sở dữ liệu (thiết lập một database test riêng biệt)
+ Có thể sử dụng file system (test việc import/export file, file permission...)

> Với những ứng dụng nhỏ, số lượng code không quá nhiều, chúng ta có thể chỉ cần thực hiện integration tests mà sử dụng database connection, lợi ích là giảm effort mà vẫn giữ được tốc độ của cả quá trình testing.

![](./images/ut-vs-it.gif)
![](./images/2toilets.jpg)

---
Index
1. **[Giới thiệu automation test, unit test và integration test](./01-automation-test.md)**
2. [Testcase và cách viết test case](./02-testcase.md)
3. [Giới thiệu và sử dụng phpunit, assertion](./03-phpunit.md)
4. [Tạo report Code Coverage](./04-code-coverage.md)
5. [Test doubles và dependency injection](./05-mock-stub-dependency-injection.md)
6. [Laravel, test như thế nào?](./06-laravel.md)
7. [Tài liệu tham khảo](./07-references.md)
8. [Example: unit test chi tiết cho chức năng đăng ký](./08-example-workflow.md)
---
