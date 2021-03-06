# Convention
- Cấu trúc thư mục
  + Tất cả Unit Tests được đặt trong thư mục `tests/Unit` (xem config testsuite trong `phpunit.xml`)
  + Tất cả Integration Tests được đặt trong thư mục `tests/Integration`
  + Nội dung bên trong thư mục `Unit` có cấu trúc giống với cấu trúc bên trong thư mục `app`. Ví dụ như Unit Test cho file `app/Models/User.php` tương ứng là `tests/Unit/Models/UserTest.php`
- Quy tắc đặt tên
  + Thường có namespace bắt đầu với `Tests\` (xem phần `autoload-dev` trong composer.json)
  + Method test phải được bắt đầu bằng `test`, viết dạng `camelCase` hay `snake_case` đều được, không phải quá lo lắng về tên method test quá dài, nhưng nên chọn 1 trong hai cho thống nhất, prefer `snake_case` để cho dễ đọc hơn:
   ```php
   public function test_it_throws_an_exception_when_email_is_too_long()
   {
   }
   ```

# Test Model
## Test accessors and mutators
VD:
```php
class User extends Model
{
    public function setPasswordAttribute($password)
    {
        $this->attributes['password'] = Hash::make($password);
    }
}

class UserTest extends TestCase
{
    public function testHashesPasswordWhenSet()
    {
        Hash::shouldReceive('make')->once()->andReturn('hashed');

        $author = new User;
        $author->password = 'foo';

        $this->assertEquals('hashed', $author->password);
    }
}
```

Ở đây method `Hash::make()` được mock, có thể hiểu như sau: Tôi mong muốn `make()` method của class `Hash` được gọi 1 lần và khi được gọi nó trả về giá trị stub là `hashed` nhằm mục đích để biết chính xác kết quả trả về và assert với kết quả thực tế của method `setPasswordAttribute`.

## Test scopes
```php
class User extends Model
{
    public function scopeOldest($query)
    {
        return $query->orderBy('age', 'desc');
    }
}
```
Mr. JeffreyWay có đưa ra một [thảo luận](https://gist.github.com/JeffreyWay/5674014), và đưa ra một vài khả năng:
1. Không cần test method này?
2. Thực hiện integration test với test database, bằng cách insert một vài record vào db, gọi method và assert method trả về correct row.
3. Partial mock class User và verify method `orderBy` được gọi.

Hầu hết các ý đều đồng tình với cách thứ 2, vì:
- Chỉ đơn giản chúng ta đang sử dụng các unit của Eloquent/QueryBuilder mà không có logic gì phức tạp
- Eloquent và QueryBuilder đều đã được test ở mức độ unit test để kiểm chứng tính đúng đắn khi chạy độc lập
- Nếu thực mock, chúng ta sẽ expect method `orderBy` được gọi với 2 tham số `age` và `desc` nhưng làm sao để biết `age` có đúng là 1 trường trong database?

Vì thế, integration test là cách hợp lý nhất ở đây:
```php
class UserTest extends TestCase
{
    public function testGetsOldestUser()
    {
        // Arrange: Insert two test rows into a test DB
        Factory::create('User', ['age' => 20]);
        Factory::create('User', ['age' => 30]);

        // Act: call the method
        $oldest = User::oldest()->first();

        // Assert
        $this->assertEquals(30, $oldest->age);
    }
}
```

## Test relationships
Thực hiện assert kết quả của các relation thuộc class tương ứng:
- `HasMany`
- `BelongsTo`
- `BelongsToMany`
- ...

## Test custom methods
VD1:
```php
class User extends Model
{
    public function fullname()
    {
        return $this->firstname . ' - ' . $this->lastname;
    }
}

class UserTest extends TestCase
{
    public function testGetFullName()
    {
        $user = new User;
        $user->firstname = 'Edogawa';
        $user->lastname = 'Conan';

        $this->assertEquals('Edogawa Conan', $user->fullname());
    }
}
```
## Integration test cho việc thiết lập `fillable`, `hidden`, `casts`
- Unit test đã được framework thực hiện
- Tương tự khi test scope, chúng ta thực hiện integration test để đảm bảo các field được mapping chính xác với DB schema

# Test Form request
- Không thực hiện unit test nếu form request chỉ khai báo `rules()` và không có logic gì đặc biệt? Vì việc test các rule validation đã được Laravel thực hiện trong core framework, chúng ta chỉ khai báo sử dụng nó!
- Một cách khác để thực hiện unit test đó là tự validate FormRequest thông qua `Validator::make()`, tuy nhiên nên nhớ là FormRequest có thể override lại method `getValidatorInstance()` để thực hiện một số logic khác. VD chi tiết tại đây: https://gist.github.com/tuanpht/37394e0df10d48d08d0d406c9a7dd477
- Thay vào đó chúng ta thực hiện integration test khi test controller, vì mục đích của class form request thường chỉ để tách phần khai báo rule ra khỏi controller, nên cần test tích hợp để nó có hoạt động như mong muốn khi kết hợp với controller hay không, response khi validate failed là gì...

# Test Controller
Nhắc lại về nhiệm vụ của Controller:
- Điều hướng HTTP request
- Gọi đến domain object để thực hiện logic và lấy kết quả trả về (Fat Model - Skinny Controller)

Ngoài ra các action như thêm/sửa thường sử dụng thêm FormRequest.

Với cách làm trên, ta có thể viết test như sau:

## Unit test
Do unit test không sử dụng database test nên nếu bạn sử dụng trực tiếp Eloquent Model trong controller thì sẽ khó thực hiện unit test. Nên cân nhắc tách ra 1 tầng trung gian (repository, service)

- Test trả về đúng view, data
```php
class ProductController extends Controller
{
    private $productService;

    public function __construct(ProductService $productService)
    {
        //
    }

    public function index(Request $request)
    {
        return view('product.index')->with(
            'products',
            $this->productService->list($request->all())
        );
    }
}

class ProductControllerTest extends TestCase
{
    public function testIndexReturnView()
    {
        $productService = Mockery::mock(ProductService::class);
        $controller = new ProductController($productService);
        $request = Request::create('');

        $view = $controller->index($request);

        $this->assertEquals('product.index', $view->name());
        $this->assertArrayHasKeys('products', $view->getData());
    }
}
```

- Test redirection
```php
public function testRedirectAfterCreatedSuccess()
{
    $this->assertInstanceOf(RedirectResponse::class, $response);
    $this->assertEquals(route('product.index'), $response->headers->get('Location'));
}

public function testRedirectAfterCreatedFailed()
{
    $this->assertInstanceOf(RedirectResponse::class, $response);
    $this->assertEquals(route('product.create'), $response->headers->get('Location'));
}
```

## Integration Test
- Do ta đã tách logic DB sử dụng repository (service) nên integration test controller có thể mock repository và thực hiện test repository integrate với DB riêng biệt
- Thực hiện test tích hợp với FormRequest, tạo ra các input mẫu và test các trường hợp input đúng, input sai, assert response tương ứng với từng trường hợp
- Test authentication

Ví dụ trong Laravel:
- Đăng nhập bằng một user
- Gửi POST request đến URL `/api/news` với payload `{"title": "Hot fake news!", "content": "Lorem Ipsum..."}`
- Response trả về thành công (HTTP code 200)
- Thêm mới bản ghi vào bảng news
- Gửi mail newsletter cho những user đăng ký

```php
namespace Tests\Integration;

use Tests\TestCase;
use App\Models\User;
use App\Jobs\SendNewsletter;
use Illuminate\Foundation\Testing\DatabaseTransactions;

class NewsApiTest extends TestCase
{
    use DatabaseTransactions;

    protected $user;

    public function setUp()
    {
        parent::setUp();

        $this->user = factory(User::class)->create();
    }

    public function test_an_user_can_create_new_servers()
    {
        $data = [
            'title' => 'Hot fake news!',
            'content' => 'Lorem Ipsum...',
        ];

        $this->expectsJobs(SendNewsletter::class);

        $this->actingAs($this->user, 'api')
            ->postJson('/api/news', $data)
            ->assertStatus(200);

        $this->assertDatabaseHas('news', [
            'user_id' => $this->user->id,
            'title' => $data['title'],
            'content' => $data['content'],
        ]);
    }
}
```

Có thể sử dụng SQLite in-memory database khi thực hiện integration test thay vì MySQL để cải thiện hiệu năng và tăng tốc quá trình test. Tuy nhiên có một chú ý là database migration hay database seed phải được chạy trong quá trình test chứ không thể chạy trước khi chạy test. Khi đó chúng ta sử dụng trait `RefreshDatabase` để vừa refresh database sau mỗi test case. Còn khi sử dụng database test là MySQL thì thực hiện migration trước khi và sử dụng `DatabaseTransactions` thay vì phải chạy lại migration trong mỗi test case.

Có thể tạo 1 trait chung cho test DB:
```php
trait SetupDatabaseTrait
{
    use RefreshDatabase; // For SQLite in memory
    // use DatabaseTransactions; // For other DB connections
}

class NewsApiTest extends TestCase
{
    use SetupDatabaseTrait;
}
```
Khi muốn chuyển đổi database test giữa MySQL và SQLite thì chỉ cần sửa lại trong trait `SetupDatabaseTrait`.

---
Index
1. [Giới thiệu automation test, unit test và integration test](./01-automation-test.md)
2. [Testcase và cách viết test case](./02-testcase.md)
3. [Giới thiệu và sử dụng phpunit, assertion](./03-phpunit.md)
4. [Tạo report Code Coverage](./04-code-coverage.md)
5. [Test doubles và dependency injection](./05-mock-stub-dependency-injection.md)
6. **[Laravel, test như thế nào?](./06-laravel.md)**
7. [Tài liệu tham khảo](./07-references.md)
8. [Example: unit test chi tiết cho chức năng đăng ký](./08-example-workflow.md)
---
