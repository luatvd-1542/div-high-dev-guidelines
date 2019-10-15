### Hamcrest Matchers là gì ?

**Matcher** là một đối số của phương thức **Assert.assertThat()**. Dùng để so sánh một giá trị thực tế có thõa mãn với một org.hamcrest.Matcher được xác định hay không. Với matchers có thể kiểm tra kết quả của một string, number, collections…

**Hamcrest** là một thư viện của các Matcher, là một tiện ích bổ sung bên ngoài của JUnit Framework và JUnit 4.12, nó cung cấp các static matchers để giúp chúng ta kiểm tra kết quả của Unit Test đơn giản hơn và dễ đọc hơn.

Ví dụ các cách viết dưới đây là tương đương giữa sử dụng các phương thức có sẵn của JUnit 4 và sử dụng thư viện Hamcrest:

~~~java
// JUnit 4 for equals check
assertEquals(expected, actual);
 
// Hamcrest for equals check
assertThat(actual, is(equalTo(expected)));
~~~
~~~java
// JUnit 4 for not equals check
assertNotEquals(expected, actual)
 
// Hamcrest for not equals check
assertThat(actual, is(not(equalTo(expected))));
~~~

### Sử dụng Hamcrest Matchers

##### Các loại Matchers

Bên dưới là một vài phương thức hỗ trợ của Matcher:

* Core
* Logical
* Object
* Beans
* Collections
* Number
* Text
* Sugar


