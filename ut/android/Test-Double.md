## Mở đầu
Bản chất của Unit test là kiểm thử đơn vị chỉ mình nó làm sao chạy được mà không bị phụ thuộc vào một module hay đơn vị khác khi run test. Khi xây dựng ứng dụng, khi một hàm của class A được sử dụng trong hàm class B, dẫn đến class B phụ thuộc class A. Trong nguyên lý **SOLID**, chữ cái **D** có nghĩa là **Dependency**.
> //Dependency Inversion Principle
>
>Các module cấp cao không nên phụ thuộc vào các module cấp thấp. Cả 2 nên phụ thuộc vào abstraction.
>Interface (abstraction) không nên phụ thuộc vào chi tiết, mà ngược lại. (Các class giao tiếp với nhau thông qua interface, >không phải thông qua implementation.)

Mọi người có thể tham khảo thêm về [Dependency](https://en.wikipedia.org/wiki/Dependency_injection)

### Vậy khó kiểm tra như thế nào ?

Đơn giản: Khi một phụ thuộc có tác dụng phụ. Ý của câu này, một chức năng đang gọi cái gì đấy bên ngoài, chẳng hạn như call API, truy vấn database, kiểm tra trạng thái status... sẽ gây ra những kết quả không như mong đợi. Trong phạm vi Unit Test chúng ta sẽ không xử lý được vấn đề đó 

=> Điều đó sinh ra **Test Double**

## Test Double
**Định nghĩa**: Trong công nghệ phần mềm và khoa học máy tính, các lập trình viên sử dụng một kỹ thuật gọi là kiểm thử đơn vị tự động (Automation Test) để nâng cao chất lượng của phần mềm. Thông thường, phần mềm lúc release bao gồm một tập hợp các đối tượng hoặc phương thức phức tạp liên kết với nhau để tạo ra kết quả cuối cùng. Trong Automation Test, có thể cần phải sử dụng các đối tượng hoặc phương thức giả trông giống và có tương tác giống như các đối tượng dự định như lúc release của chúng, nhưng thực sự là các phiên bản đơn giản hóa để giảm độ phức tạp và tạo điều kiện cho việc test. Một kiểm tra kép là một thuật ngữ chung (meta) được sử dụng cho các đối tượng hoặc phương thức này.

## Các loại Test Double
- **Dummy object** là các đối tượng được di chuyển khắp nơi nhưng không bao được sử dụng thật sự. Thường chúng chỉ được sử dụng để truyền danh sách tham số.
- **Fake object** là các đối tượng thật đã được cài đặt một cách đầy đủ, nhưng thường được sử dụng trong môi trường test, không phù hợp cho môi trường thật.

![Fake](https://cdn.softwaretestinghelp.com/wp-content/qa/uploads/2018/12/Fakes.jpeg)

- **Stub object** là một chương trình hoặc thành phần giả lập dùng để kiểm thử, nó cung cấp câu trả lời cho các cuộc gọi trong khi kiểm thử. Thông thường nó không đáp lại bất kỳ thứ gì ngoài những gì đã được lập trình cho kiểm thử.

![Stub](https://cdn.softwaretestinghelp.com/wp-content/qa/uploads/2018/12/Stubs-1.jpeg)

- **Mock object (MO)** là một đối tượng, dùng để giả lập hành vi của các class bên ngoài để thực hiện hành vi mà chúng ta mong muốn. Những giả lập này do chúng ta quản lý nên nó đảm bảo chất lượng của những đoạn code mà chúng ta đang viết Unit Test.

- **Spy object** : là một trường hợp đặc biệt của **Stub** và **Mock**, nó có thể gọi thực sự behavior của dependency hoặc mock một số behavior nếu cần.

![Spy](https://cdn.softwaretestinghelp.com/wp-content/qa/uploads/2018/12/Spies.jpg)

Một trong những Framework hỗ trợ Test Double tốt nhất trên Android là [Mockito](https://github.com/huongpt-1499/android_testing_samplecode/wiki/Mockito) 