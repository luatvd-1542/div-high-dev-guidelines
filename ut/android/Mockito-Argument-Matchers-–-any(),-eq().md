Mockito cho phép chúng ta tạo các đối tượng mock và sơ khai hành vi cho các trường hợp test của chúng tôi. Chúng tôi thường mock hành vi bằng cách sử dụng when() và thenReturn () trên đối tượng Mock.
![when-thenReturn](https://www.vogella.com/tutorials/Mockito/img/xwhenThenReturn10.png.pagespeed.ic.LBISVu4xW-.webp)

~~~java
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@Test
public void test1()  {
        //  create mock
        MyClass test = mock(MyClass.class);

        // define return value for method getUniqueId()
        when(test.getUniqueId()).thenReturn(43);

        // use mock in test....
        assertEquals(test.getUniqueId(), 43);
}


// demonstrates the return of multiple values
@Test
public void testMoreThanOneReturnValue()  {
        Iterator<String> i= mock(Iterator.class);
        when(i.next()).thenReturn("Mockito").thenReturn("rocks");
        String result= i.next()+" "+i.next();
        //assert
        assertEquals("Mockito rocks", result);
}

// this test demonstrates how to return values based on the input
@Test
public void testReturnValueDependentOnMethodParameter()  {
        Comparable<String> c= mock(Comparable.class);
        when(c.compareTo("Mockito")).thenReturn(1);
        when(c.compareTo("Eclipse")).thenReturn(2);
        //assert
        assertEquals(1, c.compareTo("Mockito"));
}

// this test demonstrates how to return values independent of the input value

@Test
public void testReturnValueInDependentOnMethodParameter()  {
        Comparable<Integer> c= mock(Comparable.class);
        when(c.compareTo(anyInt())).thenReturn(-1);
        //assert
        assertEquals(-1, c.compareTo(9));
}

// return a value based on the type of the provide parameter

@Test
public void testReturnValueInDependentOnMethodParameter2()  {
        Comparable<Todo> c= mock(Comparable.class);
        when(c.compareTo(isA(Todo.class))).thenReturn(0);
        //assert
        assertEquals(0, c.compareTo(new Todo(1)));
}
~~~

Chúng ta có thể sử dụng when() và thenReturn() để bắn ra ngoại lệ mong muốn .

~~~java
Properties properties = mock(Properties.class);

when(properties.get(”Anddroid”)).thenThrow(new IllegalArgumentException(...));

try {
    properties.get(”Anddroid”);
    fail(”Anddroid is misspelled”);
} catch (IllegalArgumentException ex) {
    // good!
}
~~~

## Mockito Argument Matchers – any()
Đôi khi chúng tôi muốn mock một hành vi của đối tượng với mọi dữ liệu đầu vào mà ta quy định. Trong trường hợp này, chúng tôi có thể sử dụng **Mockito argument matchers** . Phương thức Mockito argument được định nghĩa trong **org.mockito.ArgumentMatchers** dưới dạng một hàm static.
Chúng ta định nghĩa một class như sau:

~~~java
class Foo {
	boolean bool(String str, int i, Object obj) {
		return false;
	}

	int in(boolean b, List<String> strs) {
		return 0;
	}
	
	int bar(byte[] bytes, String[] s, int i) {
		return 0;
	}
}
~~~
Hãy cùng xem một số ví dụ về việc sử dụng các đối số đối số mockito để stub các hành vi của chúng.

~~~java
Foo mockFoo = mock(Foo.class);
when(mockFoo.bool(anyString(), anyInt(), any(Object.class))).thenReturn(true);
~~~
Chúng tôi đã giả lập hành vi của phương thức bool() trả về "true" với đầu vào là mọi String, Integer và Object.

~~~java
assertTrue(mockFoo.bool("A", 1, "A"));
assertTrue(mockFoo.bool("B", 10, new Object()));
~~~

## Mockito Argument Matcher – eq()
Nếu sử dụng argument matchers, thì với mọi đối số đầu sẽ sử dụng để so sánh. Nếu bạn muốn sử dụng một giá trị của thể cho một đối số, có thể sử dụng hàm **eq()**

~~~java
when(mockFoo.bool(eq("false"), anyInt(), any(Object.class))).thenReturn(false);
assertFalse(mockFoo.bool("false", 10, new Object()));
~~~

## Mockito Verify Argument Matchers
Chỉ có thể sử dụng các **Mockito argument matchers** với các phương thức **when()** và **verify()**. Hãy cùng xem một vài ví dụ về việc sử dụng các đối số đối số trong phương thức **verify()** Mockito.

~~~java
verify(mockFoo, atLeast(0)).bool(anyString(), anyInt(), any(Object.class));
verify(mockFoo, atLeast(0)).bool(eq("false"), anyInt(), any(Object.class));
~~~

## Tổng kết
Các phương thức so khớp đối số Mockito rất hữu ích trong việc giả lập các hành vi theo cách chung. Có nhiều phương pháp để đáp ứng hầu hết tất cả các yêu cầu.