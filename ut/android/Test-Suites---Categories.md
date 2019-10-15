Thông thường một class test sẽ sử dụng để test cho một chức năng, một unit. Nếu chúng ta có một vài test class, và mong muốn có thể kết hợp chúng thành một nhóm/ bộ kiểm tra. Chúng ta có thể làm được điều này bằng cách sử dụng **Test Suite** hoặc **Categories Test**.

### Test Suite

Để sử dụng Test Suite, chúng ta sẽ đánh dấu một annotation @RunWith và @SuiteClasses ở mức class. Chẳng hạn @RunWith(Suite.class) và @SuiteClasses(TestClass1.class, …). Khi chạy lớp này, nó sẽ chạy tất cả các test method trong tất cả các test class được chỉ định.

Ví dụ :

~~~java
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  TestFeatureLogin.class,
  TestFeatureLogout.class,
  TestFeatureNavigate.class,
  TestFeatureUpdate.class
})

public class FeatureTestSuite {
  // the class remains empty,
  // used only as a holder for the above annotations
}
~~~

### Categories Test

JUnit Categories cho phép nhóm các phương thức test/ class test lại với nhau để chúng ta có thể chạy chúng trong các test suite khác nhau.

Từ một nhóm các test class, Categories runner chỉ chạy các lớp và phương thức được đánh dấu với **@Category** và được khai báo với **@IncludeCategory**. Chúng ta cũng có thể loại trừ các Categories bằng cách sử dụng chú thích **@ExcludeCategory**.

Sự khác biệt giữa Test Suite và Categories là: Test Suite chạy tất cả các phương thức được chỉ định trong class test (ngoại trừ được chỉ định Ignore), trong khi đó Categories cho phép chúng ta có thể đánh dấu trên từng phương thức và chỉ chạy với các phương thức được đánh dấu.

Để sử dụng một Categories chúng ta sẽ thực hiện theo các bước sau:

* **Marker Interface**: Tạo một class/ interface để đánh dấu.
* **@Category** : sử dụng annotation @Category để đánh dấu phân loại, có thể đánh dấu ở mức method hoặc mức class.
* **Test Suite** : tạo một test suite và đánh dấu annotation @Categories.IncludeCategory để thực thi tất cả các phương thức test có category được chỉ định hoặc **@Categories.ExcludeCategory** để thực thi tất cả các phương thức test không có category được chỉ định.

Ví dụ :

~~~java
public interface FastTests { /* category marker */ }
public interface SlowTests { /* category marker */ }

public class A {
  @Test
  public void a() {
    fail();
  }

  @Category(SlowTests.class)
  @Test
  public void b() {
  }
}

@Category({SlowTests.class, FastTests.class})
public class B {
  @Test
  public void c() {

  }
}

@RunWith(Categories.class)
@IncludeCategory(SlowTests.class)
@SuiteClasses( { A.class, B.class }) // Note that Categories is a kind of Suite
public class SlowTestSuite {
  // Will run A.b and B.c, but not A.a
}

@RunWith(Categories.class)
@IncludeCategory(SlowTests.class)
@ExcludeCategory(FastTests.class)
@SuiteClasses( { A.class, B.class }) // Note that Categories is a kind of Suite
public class SlowTestSuite {
  // Will run A.b, but not A.a or B.c
}
~~~

