# Test case
> Quote from: https://github.com/framgia/laravel-test-guideline/blob/master/vn/Knowledge.md

Trước khi tạo bất cứ Test Cases nào, chúng ta nên xác định rõ giá trị đầu vào của **từng function/method** cần được test.

Các Test Cases phải được thiết kế để có thể cover được hết các sự kết hợp của các giá trị inputs cùng các điều kiện, bao phủ hết các nhánh if/else.

Nhìn chung, chúng ta thường chia test case ra làm 3 loại dựa trên dữ liệu inputs cho Unit Test.

- **Normal**: Inputs thuộc vào dải dữ liệu bình thường (accepted). Một lượng lớn codes có thể được cover bằng cách chỉ cần chạy **normal** test cases.
- **Boundary**: Inputs bằng hoặc xấp xỉ giá trị maximum hay minimum. Chúng được sử dụng để phát hiện lỗi tại cận, thay vì tìm kiếm lỗi tại những vị trí ở giữa trong dải input.
- **Abnormal**: Inputs là không hợp lệ hay không được kỳ vọng, dùng để kiểm tra khả năng handle lỗi.

Ví dụ: *Giả sử như chúng ta có một function để kiểm tra địa chỉ email nhập vào từ user. Độ dài tối đa của email là 50 ký tự.*

```php
function validate($email) {
    if (filter_var($email, FILTER_VALIDATE_EMAIL) && strlen($email) <= 50) {
        return true;
    }
    return false;
}

```

Chúng ta nên viết các Test Cases như sau:

<details>
    <summary>Normal cases</summary>

```php
public function test_valid_email_format_and_length()
{
    // Email with length 18 (less than: maximum - 1)
    $email = 'sample@framgia.com';
    $this->assertEquals(true, validate($email));
}
```

</details>

<details>
    <summary>Boundary cases</summary>

```php
public function test_valid_email_format_and_length_max_minus()
{
    // Email with length 49 (maximum - 1)
    $email = 'samplesamplesamplesamplesamplesamples@framgia.com';
    $this->assertEquals(true, validate($email));
}

public function test_valid_email_format_and_length_max()
{
    // Email with length 50 (equal maximum)
    $email = 'samplesamplesamplesamplesamplesamplesa@framgia.com';
    $this->assertEquals(true, validate($email));
}

public function test_valid_email_format_and_length_max_plus()
{
    // Email with length 51 (maximum + 1)
    $email = 'samplesamplesamplesamplesamplesamplesam@framgia.com';
    $this->assertEquals(false, validate($email));
}
```

</details>

<details>
    <summary>Abnormal cases</summary>

```php
public function test_invalid_email_format()
{
    // Invalid email format with normal length (between 0 ~ 50)
    $email = 'framgia.com';
    $this->assertEquals(false, validate($email));
}

public function test_valid_email_format_and_length_exceeded()
{
    // Email with length 54
    $email = 'samplesamplesamplesamplesamplesamplesample@framgia.com';
    $this->assertEquals(false, validate($email));
}
```

</details>

---
Index
1. [Giới thiệu automation test, unit test và integration test](./01-automation-test.md)
2. **[Testcase và cách viết test case](./02-testcase.md)**
3. [Giới thiệu và sử dụng phpunit, assertion](./03-phpunit.md)
4. [Tạo report Code Coverage](./04-code-coverage.md)
5. [Test doubles và dependency injection](./05-mock-stub-dependency-injection.md)
6. [Laravel, test như thế nào?](./06-laravel.md)
7. [Tài liệu tham khảo](./07-references.md)
8. [Example: unit test chi tiết cho chức năng đăng ký](./08-example-workflow.md)
---
