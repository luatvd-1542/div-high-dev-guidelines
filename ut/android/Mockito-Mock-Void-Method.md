Hầu hết trong mọi trường hợp Mockito when() đủ tốt để mock một hành vi của một đối tượng. Nhưng khi chúng ta mock một phương thức void, chúng ta không thể sử dụng when() cho trường hợp này

## Mockito Mock Void Method
Mockito cung cấp hàm sử dụng để mock một phương thức void.

doAnswer(): Chúng ta có thể sử dụng điều này để thực hiện một số thao tác khi một đối tượng mock được gọi và trả về void.
doThrow(): chúng ta có thể sử dụng doThrow() khi muốn một phương thức void ném ra một ngoại lệ.
Hay theo dõi ví dụ dưới đây

~~~java
public class Employee {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null)
			throw new IllegalArgumentException("Employee Name can't be null");
		this.name = name;
	}

}
~~~

## Mockito mock void method example
Phương thức Mockito doAnswer() lấy Answer như một đối số. 

~~~java
doAnswer((i) -> {
	System.out.println("Employee setName Argument = " + i.getArgument(0));
	assertTrue("Pankaj".equals(i.getArgument(0)));
	return null;
}).when(emp).setName(anyString());
~~~

Lưu ý rằng câu lệnh null trả về là bắt buộc vì chúng ta đang mock phương thức void.

## Mockito mock void method with exception
Đoạn mã dưới đây cho thấy cách sử dụng phương thức doThrow () để giả định các phương thức void với ngoại lệ

~~~java
doThrow(IllegalArgumentException.class).when(emp).setName(null);
~~~

## JUnit Mockito mock void method
Dưới đây là ví dụ trong JUnit , tôi sử dụng Mockito để mock một phương thức void
~~~java
package com.journaldev.mockito.voidmethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.journaldev.Employee;

class JUnitMockitoVoidMethod {

	@Test
	void test_mockito_void() {
		Employee emp = mock(Employee.class);

		doThrow(IllegalArgumentException.class).when(emp).setName(null);

		doAnswer((i) -> {
			System.out.println("Employee setName Argument = " + i.getArgument(0));
			assertTrue("Pankaj".equals(i.getArgument(0)));
			return null;
		}).when(emp).setName(anyString());

		when(emp.getName()).thenReturn("Pankaj");

		assertThrows(IllegalArgumentException.class, () -> emp.setName(null));

		emp.setName("Pankaj");
		assertEquals("Pankaj", emp.getName());
	}

}
~~~

