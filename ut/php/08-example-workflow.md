# TODO
Thực hiện unit test cho chức năng đăng ký:
- Form gồm các thông tin:
  - Tên
  - Địa chỉ email
  - Password (tối thiểu 8 ký tự)
  - Avatar (upload image png, jpg, tối đa 1MB)
- Sau khi đăng ký sẽ gửi mail xác nhận

# Bắt đầu
Ở đây chúng ta sẽ sử dụng controller và view mặc định của Laravel cho phần đăng ký (Laravel 6):
```bash
composer require --dev laravel/ui
php artisan ui --auth bootstrap
```

# Refactor `RegisterController`
Controller `RegisterController` mặc định của Laravel đang gọi Model create user trực tiếp trong controller.

Nhớ lại, chúng ta nên ưu tiên viết unit test để làm cho test case đơn giản và làm cho tốc độ test được nhanh hơn. Unit test sẽ không sử dụng database connection.

Vì Laravel Eloquent phụ thuộc vào database nên nếu gọi trực tiếp Model trong controller thì rất khó thực hiện Unit test mà chỉ có thể thực hiện Integration test, vì rất khó để thực hiện mocking Eloquent Model.

Do đó, chúng ta sẽ thực hiện refactor controller này sử dụng Model Service và Dependency Injection để có thể dễ dạng mock dependency.

---
Index
1. [Giới thiệu automation test, unit test và integration test](./01-automation-test.md)
2. [Testcase và cách viết test case](./02-testcase.md)
3. [Giới thiệu và sử dụng phpunit, assertion](./03-phpunit.md)
4. [Tạo report Code Coverage](./04-code-coverage.md)
5. [Test doubles và dependency injection](./05-mock-stub-dependency-injection.md)
6. [Laravel, test như thế nào?](./06-laravel.md)
7. [Tài liệu tham khảo](./07-references.md)
8. **[Example: unit test chi tiết cho chức năng đăng ký](./08-example-workflow.md)**
---
