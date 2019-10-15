## Mockito là gì?
- là một framework hỗ trợ tạo unit test bằng cách sử dụng các đối tượng giả (Mock hay TestDouble) theo cách dễ sử dụng mà không tạo ra “nhiễu” từ các tương tác không liên quan.

## Tại sao cần sử dụng Mockito
- Phá vỡ sự phụ thuộc
- Làm việc song song
- Không cần phải "hard code" giá trị của class phụ thuộc
- Chạy Unit testing nhanh hơn
- Cải thiện thiết kế code

## Lợi ích của Mockito
- Đơn giản: dễ dàng tạo các đối tượng giả và giả lập kết quả, hành vi test.
- Số lượng API của Mockito không nhiều, nhưng đáp ứng đầy đủ các yêu cầu để giả lập các hành vi test.
- Tập trung vào test các hành vi cụ thể, giảm thiểu các phiền nhiễu từ các tương tác không liên quan.
- Hỗ trợ trả về giá trị và ngoại lệ.
- Thiết lập dễ dàng
- Dễ học

## Kịch bản của Mockito
![Mockito Concept](https://scontent.fhan2-1.fna.fbcdn.net/v/t1.15752-9/68884753_494327724661774_8924112566939025408_n.png?_nc_cat=103&_nc_oc=AQkJwoODC89a_IVxQVjgI_Vg3POBc1A2T7LCxFhl8AYAQJEAC88-dT-CgLpCp2nTwyk&_nc_ht=scontent.fhan2-1.fna&oh=c7ad394c54b19203d6ea23e831e387a3&oe=5DA42E99)

## Triển khai Mockito trong Android
Để sử dụng Mockito chúng ta cần thêm thư viện này vào project. Trong file Gradle chúng ta thêm đoạn code sau:
### Using Gradle for a Java project
~~~java
repositories { jcenter() }
dependencies { testImplementation 'org.mockito:mockito-core:2.7.22' }
~~~

### Using Gradle for an Android project
~~~java
dependencies {
 // ... more entries
 testCompile 'junit:junit:4.12'
 // required if you want to use Mockito for unit tests
 testCompile 'org.mockito:mockito-core:2.7.22'
 // required if you want to use Mockito for Android tests
 androidTestCompile 'org.mockito:mockito-android:2.7.22'
}
~~~
