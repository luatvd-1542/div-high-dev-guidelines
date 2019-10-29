# RSpec Base

## RSpec Core

### What is The RSpec Core?

- Provides the structure for writing executable examples of how your code
should behave.
- An rspec command with tools to constrain which examples get run and
tailor the output.

### Install

```bash
gem install rspec

# OR
gem install rspec-core

# OR
%w[rspec rspec-core rspec-expectations rspec-mocks rspec-support].each do |lib|
  gem lib, :git => "git://github.com/rspec/#{lib}.git", :branch => 'master'
end
```

### Basic Structure

    "Describe an order."
    "It sums the prices of its line items."

```ruby
RSpec.describe Order do
    it "sums the prices of its line items" do
        order = Order.new

        order.add_entry(LineItem.new(item: Item.new(
        price: Money.new(1.11, :USD)
        )))
        expect(order.total).to eq(Money.new(5.55, :USD))
    end
end
```

Besides, RSpec also provides us with syntax such as:
- context
- Before
- Let
- Subject
- ...

```ruby
describe UsersController do
    let(:user) {FactoryGirl.create :user}

    describe "GET show" do
        before {get :show, {id: user.id}}

        subject {response}

        context "responds successfully with an HTTP 200 status code" do
            it {is_expected.to be_success}
            it {is_expected.to have_http_status 200}
        end
        
        context "renders the show view" do
            it {is_expected.to render_template :show}
        end
    end
end
```

### Nested Groups

You can also declare nested nested groups using the describe or context
methods:

```ruby
RSpec.describe Order do
    context "with no items" do
        it "behaves one way" do
        # ...
        end

    end
    
    context "with one item" do
        it "behaves another way" do
        # ...
        end
    end
end
```

### Examples and Contexts

- The object returned by it "does something" is an instance of Example
- Example group bodies (e.g. describe or context blocks) are evaluated in
the context of a new subclass of ExampleGroup.

```ruby
describe Thing do
    it "does something" do
    end
end
```

### Shared Examples and Contexts
1. Declare a shared example group using
shared_examples
2. Include it in any group using include_examples.

```ruby
RSpec.shared_examples "collections" do |collection_class|
    it "is empty when first created" do
        expect(collection_class.new).to be_empty
    end
end

RSpec.describe Array do
    include_examples "collections", Array
end

RSpec.describe Hash do
    include_examples "collections", Hash
end
```

### Metadata

- Rspec-core stores a metadata hash with every example and group
- Contains their descriptions, the locations at which they were declared

```ruby
it "does something" do |example|
    expect(example.metadata[:description]).to eq("does something")
end
```

## RSpec Expectations

### What is The RSpec expectations?

RSpec-expectations is used to define expected outcomes.

```ruby
RSpec.describe Account do
    it "has a balance of zero when first created" do
    expect(Account.new.balance).to eq(Money.new(0))
    end
end
```

### Install

```bash
gem install rspec

# OR
gem install rspec-expectations

# OR
%w[rspec rspec-core rspec-expectations rspec-mocks rspec-support].each do |lib|
gem lib, :git => "git://github.com/rspec/#{lib}.git", :branch => 'master'
end
```

### Basic structure

RSpec-expectations is used to define expected outcomes.

```ruby
expect(actual).to matcher(expected)
expect(actual).not_to matcher(expected)
```

### Matcher

A matcher is any object that responds to the following methods:

```ruby
matches?(actual)
failure_message
```

These methods are also part of the matcher protocol, but are optional:
- does_not_match?(actual)
- Failure_message_when_negated
- Description
- supports_block_expectations?

### Built-in Matchers

Equality
```ruby
expect(actual).to eq(expected) # passes if actual == expected
expect(actual).to eql(expected) # passes if actual.eql?(expected)
expect(actual).not_to eql(not_expected) # passes if not(actual.eql?(expected))
```

Identity
```ruby
expect(actual).to be(expected) # passes if actual.equal?(expected)
expect(actual).to equal(expected) # passes if actual.equal?(expected)
```

Comparisons
```ruby
expect(actual).to be > expected
expect(actual).to be >= expected
expect(actual).to be <= expected
expect(actual).to be < expected
expect(actual).to be_within(delta).of(expected)
```

Regular expressions
```ruby
expect(actual).to match(/expression/)
```

Types/Classes
```ruby
expect(actual).to be_an_instance_of(expected)
expect(actual).to be_a(expected)
expect(actual).to be_an(expected)
expect(actual).to be_a_kind_of(expected)
```

Truthiness
```ruby
expect(actual).to be_truthy # passes if actual is truthy (not nil or false)
expect(actual).to be true # passes if actual == true
expect(actual).to be_falsy # passes if actual is falsy (nil or false)
expect(actual).to be false # passes if actual == false
expect(actual).to be_nil # passes if actual is nil
expect(actual).to_not be_nil # passes if actual is not nil
```

Expecting errors
```ruby
expect { ... }.to raise_error
expect { ... }.to raise_error(ErrorClass)
expect { ... }.to raise_error("message")
expect { ... }.to raise_error(ErrorClass, "message")
```

Expecting throws
```ruby
expect { ... }.to throw_symbol
expect { ... }.to throw_symbol(:symbol)
expect { ... }.to throw_symbol(:symbol, 'value')
```

Yielding
```ruby
expect { |b| 5.tap(&b) }.to yield_control
expect { |b| yield_if_true(true, &b) }.to yield_with_no_args
expect { |b| 5.tap(&b) }.to yield_with_args(5)
expect { |b| 5.tap(&b) }.to yield_with_args(Integer)
expect { |b| "a string".tap(&b) }.to yield_with_args(/str/)
expect { |b| [1, 2, 3].each(&b) }.to yield_successive_args(1, 2, 3)
expect { |b| { :a => 1, :b => 2 }.each(&b) }.to yield_successive_args([:a, 1], [:b, 2])
```

Predicate matchers
```ruby
expect(actual).to be_xxx # passes if actual.xxx?
expect(actual).to have_xxx(:arg) # passes if actual.has_xxx?(:arg)
```

Ranges (Ruby => 1.9)
```ruby
expect(1..10).to cover(3)
```

Collection membership
```ruby
expect(actual).to include(expected)
expect(actual).to start_with(expected)
expect(actual).to end_with(expected)
expect(actual).to contain_exactly(individual, items)
# ...which is the same as:
expect(actual).to match_array(expected_array)
```

### Should syntax

```ruby
actual.should eq expected
actual.should be > 3
[1, 2, 3].should_not include 4
```

You can also create compound matcher expressions using and or or:
```ruby
expect(alphabet).to start_with("a").and end_with("z")
expect(stoplight.color).to eq("red").or eq("green").or eq("yellow")
```

