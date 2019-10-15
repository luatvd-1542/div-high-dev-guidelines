Mockito cho phép chúng ta tạo các đối tượng mock. Vì phương thức tĩnh thuộc về lớp, nên Mockito không có cách nào để giả định phương thức tĩnh. Tuy nhiên, chúng ta có thể sử dụng PowerMock cùng với khung Mockito để mock các phương thức tĩnh.

## Mockito Mock Static Method sử dụng PowerMock
PowerMock cung cấp các module khác nhau kế thừa từ Mockito framework và chúng chạy các testcase JUnit. PowerMock chưa hỗ trợ JUnit5 , vậy nên chúng tôi tạo chúng trên JUnit4.

### PowerMock Dependencies
Chúng ta cần tuân theo các phụ thuộc PowerMock để mock các phương thức tĩnh trong Mockito.

- **powermock-api-mockito2**: PowerMock được kế thừa từ Mockito2. Nếu bạn sử dụng Mockito phiên bản 1.x thì hãy sử dụng **powermock-api-mockito**
- **powermock-module-junit4**: cho các testcase chạy trên JUnit 4 và hỗ trợ PowerMock.
- **powermock-module-testng**: Cho các testcase chạy trên TestNG và hỗ trợ PowerMock.

Hãy theo dõi một class đơn giản với một phương thức tĩnh sau :
~~~java
package com.journaldev.mockito.staticmethod;

public class Utils {

	public static boolean print(String msg) {
		System.out.println("Printing "+msg);
		return true;
	}
}
~~~
### JUnit Mockito PowerMock Example
Chúng ta cần làm như sau để tích hợp PowerMock với Mockito và JUnit 4.
- Chú thích test class with **@RunWith(PowerMockRunner.class)** 
- Chú thích test class with **@PrepareForTest** và cung cấp class đã được mock sử dụng **PowerMock**.
- Sử **PowerMockito.mockStatic()** để tạo mock cho phương thức tĩnh.
- Sử dụng **PowerMockito.verifyStatic()** để **verify** phương thức tĩnh được mock sử dụng Mockito.

~~~java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Utils.class)
public class JUnit4PowerMockitoStaticTest{

	@Test
	public void test_static_mock_methods() {
		PowerMockito.mockStatic(Utils.class);
		when(Utils.print("Hello")).thenReturn(true);
		when(Utils.print("Wrong Message")).thenReturn(false);
		
		assertTrue(Utils.print("Hello"));
		assertFalse(Utils.print("Wrong Message"));
		
		PowerMockito.verifyStatic(Utils.class, atLeast(2));
		Utils.print(anyString());
	}
}
~~~

## Tổng kết
PowerMock cung cấp các tính năng mở rộng cho Mockito, một trong số đó là khả năng kiểm tra các phương thức tĩnh. Nó dễ dàng tích hợp với JUnit4 và TestNG. Tuy nhiên, chưa có kế hoạch hỗ trợ trong thời gian gần cho JUnit5.