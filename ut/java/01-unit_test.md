## Unit Test là gì?
- Unit Test – Kiểm tra mức đơn vị.

- Để có thể hiểu rõ về Unit Test, khái niệm trước tiên ta cần làm rõ: thế nào là một đơn vị PM (Unit)?

- Một Unit là một thành phần PM nhỏ nhất mà ta có thể kiểm tra được. Theo định nghĩa này, các hàm (Function), thủ tục (Procedure), lớp (Class), hoặc các phương thức (Method) đều có thể được xem là Unit.

Các bạn có thể đọc thêm [ở đây](https://techmaster.vn/posts/33618/unit-test-dung-de-lam-gi-va-kinh-nghiem-viet-unit-test-tot-nhat)

## Unit test tốt là:
- Mỗi test độc lập các thành phần vs nhau, nhớ rằng bạn chỉ thực hiện test cho một đơn vị lập trình, một function.
- Mỗi test mô tả một kịch bản test
- Đặt tên unit test một cách rõ ràng, có hệ thống. Ví dụ:
[method]_When[Input Condition]_Return[Expect Output]
- Thực hiện assert chỉ cho mục  của method, mỗi method chỉ thực hiện một mục tiêu duy nhất.

## Unit test tốt là: