Đôi khi các phương thức của chúng ta đưa ra các ngoại lệ và chúng tôi muốn mock đối tượng và kiểm tra các ngoại lệ. Chúng ta có thể sử dụng các đối tượng mock trong Mockito với **when()** và **thenThrow()** để mô phỏng kịch bản này.

## Mockito Stub Exception – JUnit 5

Hãy cùng xem một ví dụ đơn giản trong đó chúng ta sẽ mock phương thức đối tượng của mình để đưa ra một ngoại lệ. Sau đó, chúng tôi sẽ sử dụng JUnit 5 assertThrows để kiểm tra ngoại lệ và thông điệp của nó.

~~~java
package com.journaldev.mockito.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

class JUnitMockitoStubExceptions {

	@SuppressWarnings("unchecked")
	@Test
	void test() {
		List<String> list = mock(List.class);
		when(list.size()).thenThrow(new RuntimeException("size() method not supported"));

		Exception exception = assertThrows(RuntimeException.class, () -> list.size());
		assertEquals("size() method not supported", exception.getMessage());
	}
}
~~~

Để đơn giản, chúng tôi đang mock class List. Tương tự, chúng ta cũng có thể giả định bất kỳ đối tượng nào khác và chỉ định hành vi của nó để đưa ra một ngoại lệ khi một phương thức cụ thể được gọi.

## Mockito Stub Exception – TestNG
Nếu bạn sử dụng [TestNG](https://www.journaldev.com/21219/testng-tutorial) framework, 
sau đó chúng ta có thể sử dụng khẳng định assertThrows.
~~~java
@Test
void test() {
	List<String> list = mock(List.class);
	when(list.size()).thenThrow(new RuntimeException("size() method not supported"));

	assertThrows(RuntimeException.class, () -> list.size());
}
~~~
Nếu bạn muốn test ngoại tệ , chỉ cần sử dụng chú thích **@Test** với **expectedExceptions** và **expectedExceptionsMessageRegExp** như ví dụ sau:
~~~java
@Test(expectedExceptions = RuntimeException.class, expectedExceptionsMessageRegExp = "size method not supported")
void test1() {
	List<String> list = mock(List.class);
	when(list.size()).thenThrow(new RuntimeException("size method not supported"));
	list.size();
}
~~~
