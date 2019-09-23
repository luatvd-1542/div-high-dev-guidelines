# Code coverage
- Đánh giá mức độ mà source code đã được thực thi, khi chạy test (Hay độ bao phủ của test case)
- Công thức tính:
  > Code Coverage = (Tổng số dòng Code được gọi bởi các bài Tests của bạn) / (Tổng số dòng Code trong thực tế) x 100%
- Code Coverage có thể được tạo ra bằng PHPUnit với extension with Xdebug được kích hoạt. Bởi vậy, hãy chắc chắn rằng bạn đã cài đặt và bật Xdebug lên:
  ```sh
  ./vendor/bin/phpunit --coverage-html coverage/

  # Or xml output
  ./vendor/bin/phpunit --coverage-clover test-report.xml
  ```
- Hoặc generate report với `phpdbg` (apt package `php-phpdbg`) mà không cần Xdebug:
  ```sh
  phpdbg -qrr ./vendor/bin/phpunit --coverage-html coverage/
  ```
- Tham khảo: https://viblo.asia/p/php-unit-test-401-tao-bao-cao-coverage-reports-va-chi-so-crap-ByEZkWXWZQ0

> 100% code coverage is not our main purpose of test!!! 70 - 80% is ok!
>
> Quotes:
> - Quality over quantity
> - If it scares you then write test for it
> - Think how to write enough test cases
> - Think how to write test correctly
> - Think how to write simple code, simple test
> - Think how to write fast test

---
Index
1. [Automation Test](./01-automation-test.md)
2. [Testcase](./02-testcase.md)
3. [phpunit](./03-phpunit.md)
4. **[Code Coverage](./04-code-coverage.md)**
5. [Test doubles and dependency injection](./05-mock-stub-dependency-injection.md)
6. [Laravel](./06-laravel.md)
7. [References](./07-references.md)
---
