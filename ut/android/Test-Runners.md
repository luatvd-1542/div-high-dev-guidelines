### Test Runners
Bình thường, các IDE như NetBeans, Eclipse đều có sẵn trình chạy (runner) cho JUnit để hiển thị kết quả các test case. Bên cạnh đó, chúng ta có thể gọi một API được hỗ trợ từ JUnit để thực thi các class test một cách thủ công thông qua JUnitCore.

Để chạy từ chương trình Java ta sử dụng:

~~~java
org.junit.runner.JUnitCore.runClasses(TestClass1.class, ...);
~~~

Để chạy từ console ta sử dụng:

~~~java
java org.junit.runner.JUnitCore TestClass1 [...other test classes...]
~~~

### Annotation @RunWith

Khi một class được đánh dấu bằng annotation @RunWith hoặc extends một class được đánh dấu bằng @RunWith, JUnit sẽ gọi lớp mà nó tham chiếu để chạy các test trong lớp đó thay vì runner được tích hợp hẵn trong JUnit.

* Javadocs cho @RunWith :  http://junit.org/javadoc/latest/org/junit/runner/RunWith.html
* Runner mặc định là BlockJUnit4ClassRunner ( thay cho JUnit4ClassRunner cũ )
* Chú thích với annotation @RunWith(JUnit4.class) sẽ luôn gọi trình chạy JUnit 4 mặc định trong phiên bản hiện tại của JUnit, đây là lớp runner mặc định hiện tại trong JUnit4.