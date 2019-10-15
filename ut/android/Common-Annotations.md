JUnit cung cấp một số Annotation để viết Test như sau:

### @Before
Phương thực được đánh dấu với Annotation này sẽ được gọi trước mỗi khi phương thức @Test được gọi.

Nó thường được sử dụng để khởi tạo dữ liệu trước khi thực thi một phương thức @Test.

### @After
Phương thực được đánh dấu với Annotation này sẽ được gọi sau mỗi khi phương thức @Test được gọi.

Nó thường được sử dụng để dọn dẹp bộ nhớ sau khi thực thi một phương thức @Test.

### @BeforeClass
Phương thực được đánh dấu với Annotation này sẽ được gọi trước khi thực thi tất cả các phương thức @Test được gọi trong một Test class. Phương thức này chỉ được gọi một lần duy nhất.

Phương thức đánh dấu Annotation này phải là static.

Nó thường được sử dụng để khởi tạo dữ liệu cho việc thực thi một Test class.

### @AfterClass
Tương tự như @BeforeClass, nhưng nó được gọi sau khi kết thúc thực thi các phương thức @Test. Phương thức này chỉ được gọi một lần duy nhất.

Phương thức đánh dấu Annotation này phải là static.

Nó thường được sử dụng để dọn dẹp bộ nhớ sau khi thực thi tất cả các phương thức @Test trong một Test class.

### @Test
Được sử dụng để đánh dấu đây là một phương thức test.@Test(timeout=500)Được sử dụng khi cần giới hạn thời gian thực thi của một phương thức. Nếu vượt quá thời này thì phương thức sẽ fail.

### @Test(expected=XxxException.class)
Được sử dụng khi cần test một ngoại lệ được throw ra từ phương thức được test. Nếu ngoại lệ không được throw thì phương thức sẽ fail.

Ví dụ :
~~~java
new ArrayList<Object>().get(0);
~~~
~~~java
@Test(expected = IndexOutOfBoundsException.class) 
public void empty() { 
     new ArrayList<Object>().get(0); 
}
~~~

### @Ignore
Được sử dụng để đánh dấu phương thức này để được bỏ qua (ignore/ disable), không cần thực thi test.
Nó có thể sử dụng cho một phương thức test hay một class từ một test suite.
Vì một lý do nào đó, chúng ta muốn tạm thời vô hiệu hóa test case (bỏ qua/ không chạy test case đó).

Thông thường ta sẽ xóa hoặc comment annotation @Test, như thế trình test runner sẽ bỏ qua method đó nhưng đồng thời test case đó cũng sẽ không được report, chúng ta có thể quên mất là có test case đó.

Biện pháp thay thế là sử dụng annotation @Ignore ở trước hoặc sau annotation @Test, sau khi chạy JUnit test, nó vẫn thông báo là có test case đó nhưng đang bị disable. Trong trường hợp muốn ignore tất cả các phương thức của một class test, chỉ đơn giản đặt annotation @Ingore ở mức class.

~~~java
@Ignore("Test is ignored as a demonstration")
@Test
public void testSame() {
    assertThat(1, is(1));
}
~~~

### @FixMethodOrder
Annotation này cho phép user có thể chọn thứ tự thực thi các phương thức @Test trong một test class.

Các phương thức test trong một class nên được viết một cách độc lập, không phụ thuộc lẫn nhau nên thứ tự thực thi một lớp không quan trọng. Tuy nhiên, chúng ta có thể xác định thứ tự thực thi của các method trong class test bằng cách dùng annotation @FixMethodOrder ở mức class. Có 3 kiểu sắp xếp là:

* **@FixMethodOrder(MethodSorters.DEFAULT)**: Đây là kiểu sắp xếp mặc định nếu không khai báo @FixMethodOrder, tuy nhiên với kiểu này thì sẽ không thể xác định chính xác method nào sẽ được thực thi trước.

* **@FixMethodOrder(MethodSorters.JVM)**: Thứ tự các method test dựa theo JVM. Tuy nhiên thứ tự này có thể bị thay đổi khi thực thi.

* **@FixMethodOrder(MethodSorters.NAME_ASCENDING)**: Thứ tự các method được thực thi dựa theo tên method. Thông thường, nếu cần sắp xếp thì kiểu này được chọn bởi nó giữ đúng thứ tự theo tên phương thức, không bị thay đổi như 2 kiểu trên.

~~~java
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMethodOrder {

    @Test
    public void testA() {
        System.out.println("first");
    }
    @Test
    public void testB() {
        System.out.println("second");
    }
    @Test
    public void testC() {
        System.out.println("third");
    }
}
~~~

### Một số Annotation khác
Ngoài ra còn một số Annotation khác như: @Rule, @ClassRule, @RunWith, @SuiteClasses, @Parameterized, …