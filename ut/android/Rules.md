**Rule** (quy tắc) trong JUnit 4 là một thành phần cho phép chúng ta viết code để thực hiện một số công việc trước và sau khi phương thức test thực thi. Do đó, tránh duplicate code trong các lớp test khác nhau. Chúng rất hữu ích để thêm nhiều chức năng hơn cho tất cả các phương thức test trong một class test. Chúng ta có thể mở rộng hoặc sử dụng lại các rule được cung cấp hoặc viết các rule mới theo mục đích sử dụng riêng.

Tất cả các lớp Rule của JUnit 4 phải implement một interface org.junit.rules.TestRule. Một số Rule có sẵn trong JUnit 4:

* TemporaryFolder Rule
* Timeout Rule
* ExternalResource Rules và ClassRule
* ErrorCollector Rule
* Verifier Rule
* TestWatchman/TestWatcher Rules
* TestName Rule
* ExpectedException Rules
* RuleChain
* Custom Rules

Bên dưới là một số ví dụ sử dụng Rule trong JUnit :

### TImeout Rule
* Timeout rule áp dụng thời gian chờ cho tất cả các phương thức trong một lớp :

~~~java
public static class HasGlobalTimeout {
  public static String log;
  
  @Rule
  public final TestRule globalTimeout = Timeout.millis(20);
  
  @Test
  public void testInfiniteLoop1() {
    log += "ran1";
    for(;;) {}
  }
  
  @Test
  public void testInfiniteLoop2() {
    log += "ran2";
    for(;;) {}
  }
}
~~~

### TestName Rule

* TestName Rule cho phép chúng ta có lấy được tên của phương thức test hiện tại bên trong phương thức test.

~~~java
public class NameRuleTest {
  @Rule
  public final TestName name = new TestName();
  
  @Test
  public void testA() {
    assertEquals("testA", name.getMethodName());
  }
  
  @Test
  public void testB() {
    assertEquals("testB", name.getMethodName());
  }
}
~~~

### ExpectedException Rules

* ExpectedException Rules cho phép test các loại ngoại lệ và thông báo ngoại lệ mong muốn bên trong phương thức test.

~~~java
public static class HasExpectedException {
  @Rule
  public final ExpectedException thrown = ExpectedException.none();

  @Test
  public void throwsNothing() {

  }

  @Test
  public void throwsNullPointerException() {
    thrown.expect(NullPointerException.class);
    throw new NullPointerException();
  }

  @Test
  public void throwsNullPointerExceptionWithMessage() {
    thrown.expect(NullPointerException.class);
    thrown.expectMessage("happened?");
    thrown.expectMessage(startsWith("What"));
    throw new NullPointerException("What happened?");
  }
}
~~~

### Verifier Rule

* Verifier Rule thực hiện kiểm tra verfify và nếu thất bại thì phương thức test sẽ kết thúc với kết quả không thành công (fail). Chúng ta có thể viết logic để verfify riêng của mình với Verifier Rule.

~~~java
public static class UsesVerifier {
  
  private static String sequence;
  
  @Rule
  public final Verifier collector = new Verifier() {
    @Override
    protected void verify() {
      sequence += "verify ";
    }
  };

  @Test
  public void example() {
    sequence += "test ";
  }
  
  @Test
  public void verifierRunsAfterTest() {
    sequence = "";
    assertThat(testResult(UsesVerifier.class), isSuccessful());
    assertEquals("test verify ", sequence);
  }

}
~~~

### RuleChain

* Một class có thể có rất nhiều Rule. Tuy nhiên, các Rule có thể được thực thi theo bất kỳ thứ tự nào, chúng ta không thể dựa vào thứ tự khai báo làm lệnh thực thi.Thay vào đó, chúng ta có thể tạo sử dụng RuleChain để sắp xếp thứ tự thực thi các Rule như chúng ta cần.

~~~java
public static class UseRuleChain {
    @Rule
    public final TestRule chain = RuleChain
                           .outerRule(new LoggingRule("outer rule"))
                           .around(new LoggingRule("middle rule"))
                           .around(new LoggingRule("inner rule"));

    @Test
    public void example() {
        assertTrue(true);
    }
}
~~~
Kết quả :

~~~java
starting outer rule
starting middle rule
starting inner rule
finished inner rule
finished middle rule
finished outer rule
~~~

DONE


