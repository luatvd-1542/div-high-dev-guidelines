Đầu tiên - Làm thế nào để có thể sử dụng các annotation với Mockito.
Để các chú thích này được bật, chúng tôi sẽ cần chú thích kiểm tra JUnit với runner - MockitoJUnitRunner như trong ví dụ sau
~~~java
@RunWith(MockitoJUnitRunner.class)
public class MockitoAnnotationTest {
    ...
}
~~~

Mặt khác, chúng ta cũng có thể cũng có thể kích hoạt các Annotation này bằng cách sử dụng MockitoAnnotations.initMocks() như trong ví dụ dưới
~~~java
@Before
public void init() {
    MockitoAnnotations.initMocks(this);
}
~~~
## Các loại Annotation trong mockito
- **@Mock** được sử dụng để khởi tạo một đối tượng Mock. Nó làm cho code dễ đọc hơn
- **@Spy** được sử dụng để khởi tạo một đối tượng Spy. Chúng ta có thể sử dụng nó thay cho phương thức mặc định spy(Object)
- **@InjectMocks** được sử dụng để tự động khởi tạo đối tượng được kiểm tra và đưa tất cả các phụ thuộc trường chú thích - @Mock hoặc @Spy vào nó (nếu có).
- **@Captor** được sử dụng để tạo ra một ArgumentCaptor.

## @Mock Annotation
Chú thích được sử dụng rộng rãi nhất trong Mockito là @Mock. Chúng tôi có thể sử dụng @Mock để tạo và inject các trường hợp giả mà không cần phải gọi **_Mockito.mock(classToMock.class)_** theo cách thủ công. Trong ví dụ sau - chúng tôi sẽ tạo một ArrayList giả định theo cách thủ công mà không cần sử dụng chú thích @Mock:
~~~java
@Test
public void whenNotUseMockAnnotation_thenCorrect() {
    List mockList = Mockito.mock(ArrayList.class);
     
    mockList.add("one");
    Mockito.verify(mockList).add("one");
    assertEquals(0, mockList.size());
 
    Mockito.when(mockList.size()).thenReturn(100);
    assertEquals(100, mockList.size());
}
~~~
Và bây giờ, chúng tôi sẽ làm điều tương tự nhưng chúng tôi sẽ inject class mock bằng cách sử dụng chú thích @Mock:
~~~java
@Mock
List<String> mockedList;
 
@Test
public void whenUseMockAnnotation_thenMockIsInjected() {
    mockedList.add("one");
    Mockito.verify(mockedList).add("one");
    assertEquals(0, mockedList.size());
 
    Mockito.when(mockedList.size()).thenReturn(100);
    assertEquals(100, mockedList.size());
}
~~~
Lưu ý: Trong cả hai ví dụ, chúng tôi đã tương tác với Mock và xác minh một số tương tác này - chỉ để đảm bảo rằng Mock đó đang hoạt động chính xác.

##  @Spy Annotation
Bây giờ - hãy xem cách sử dụng chú thích @Spy để theo dõi một ví dụ hiện có. 
Trong ví dụ sau - chúng tôi tạo một Spy của List theo cách cũ mà không sử dụng chú thích @Spy:
~~~java
@Test
public void whenNotUseSpyAnnotation_thenCorrect() {
    List<String> spyList = Mockito.spy(new ArrayList<String>());
     
    spyList.add("one");
    spyList.add("two");
 
    Mockito.verify(spyList).add("one");
    Mockito.verify(spyList).add("two");
 
    assertEquals(2, spyList.size());
 
    Mockito.doReturn(100).when(spyList).size();
    assertEquals(100, spyList.size());
}
~~~
Vậy cũng với ví dụ đó, spy với một danh sách, nhưng sử dụng @Spy annotation
~~~java
@Spy
List<String> spiedList = new ArrayList<String>();
 
@Test
public void whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
    spiedList.add("one");
    spiedList.add("two");
 
    Mockito.verify(spiedList).add("one");
    Mockito.verify(spiedList).add("two");
 
    assertEquals(2, spiedList.size());
 
    Mockito.doReturn(100).when(spiedList).size();
    assertEquals(100, spiedList.size());
}
~~~
Lưu ý, như trước đây - chúng tôi đã tương tác với Spy ở đây để đảm bảo rằng nó hoạt động chính xác. Trong ví dụ này, chúng tôi:

Đã sử dụng phương thức thực spiedList.add () để thêm các phần tử vào spiedList.
Spy sẽ hoạt động giống một object thật, dễ nhận thấy kết quả trả về của testcase này là fail

## @Captor Annotation
Chúng ta có thể tạo cá thể **ArgumentCaptor **cho bất kỳ lớp nào, sau đó phương thức **capture()** của nó được sử dụng với các phương thức **verify()** .Cuối cùng, chúng ta có thể nhận được các đối số đã bắt từ các phương thức **getValue()** và **getAllValues()**.

Phương thức **getValue()** có thể được sử dụng khi chúng ta đã bắt được một đối số duy nhất. Nếu phương thức được xác minh được gọi nhiều lần thì phương thức **getValue()** sẽ trả về giá trị đã bắt mới nhất.

Nếu có nhiều đối số được capture lại, hãy gọi **getAllValues​​()** để nhận danh sách các đối số.
~~~java
class MathUtils {
	public int add(int x, int y) {
		return x + y;
	}

	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public long squareLong(long l) {
		return l*l;
	}
}
~~~

Chúng ta có thể tạo file test sử dụng ArgumentCaptor như bên dưới

~~~java
@Test
void test() {
	MathUtils mockMathUtils = mock(MathUtils.class);
	when(mockMathUtils.add(1, 1)).thenReturn(2);
	when(mockMathUtils.isInteger(anyString())).thenReturn(true);

	ArgumentCaptor acInteger = ArgumentCaptor.forClass(Integer.class);
	ArgumentCaptor acString = ArgumentCaptor.forClass(String.class);

	assertEquals(2, mockMathUtils.add(1, 1));
	assertTrue(mockMathUtils.isInteger("1"));
	assertTrue(mockMathUtils.isInteger("999"));

	verify(mockMathUtils).add(acInteger.capture(), acInteger.capture());
	List allValues = acInteger.getAllValues();
	assertEquals(List.of(1, 1), allValues);
	
	verify(mockMathUtils, times(2)).isInteger(acString.capture());
	List allStringValues = acString.getAllValues();
	assertEquals(List.of("1", "999"), allStringValues);
}
~~~
### Mockito @Captor
Chúng ta có thể sử dụng @Captor thay cho việc sử dụng ArgumentCaptor.forClass(class) như thông thường
~~~java
class MockitoArgumentCaptorExamples {

	@Captor ArgumentCaptor acLong;

	@Test
	void test() {
		MathUtils mockMathUtils = mock(MathUtils.class);
		when(mockMathUtils.squareLong(2L)).thenReturn(4L);
		assertEquals(4L, mockMathUtils.squareLong(2L));
		verify(mockMathUtils).squareLong(acLong.capture());
		assertTrue(2 == acLong.getValue());
	}
}
~~~

## @InjectMocks Annotation
Mockito **@InjectMocks** Mockito cố gắng **inject** các phụ thuộc bị giả định bằng một trong ba cách tiếp cận, theo thứ tự được chỉ định. 
- Trình xây dựng dựa trên hàm tạo - khi có một hàm tạo được định nghĩa cho lớp, Mockito cố gắng thêm các phụ thuộc bằng cách sử dụng hàm tạo lớn nhất. 
- Dựa trên các phương thức Setter - khi không có hàm tạo nào được xác định, Mockito cố gắng thêm các phụ thuộc bằng các phương thức setter. 
- Dựa trên trường - nếu không constructor hoặc inject trên trường có thể, thì mockito cố gắng inject các phụ thuộc vào chính trường đó. Nếu chỉ có một đối tượng giả phù hợp, thì mockito sẽ inject nó vào đối tượng. Nếu có nhiều hơn một đối tượng mock của cùng một lớp, thì tên đối tượng mock được sử dụng để thêm các phụ thuộc.

~~~java
@Mock
Map<String, String> wordMap;
 
@InjectMocks
MyDictionary dic = new MyDictionary();
 
@Test
public void whenUseInjectMocksAnnotation_thenCorrect() {
    Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");
 
    assertEquals("aMeaning", dic.getMeaning("aWord"));
}
~~~
và đây là class MyDictionary
~~~java
public class MyDictionary {
    Map<String, String> wordMap;
 
    public MyDictionary() {
        wordMap = new HashMap<String, String>();
    }
    public void add(final String word, final String meaning) {
        wordMap.put(word, meaning);
    }
    public String getMeaning(final String word) {
        return wordMap.get(word);
    }
}
~~~