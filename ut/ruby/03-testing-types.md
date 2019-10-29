# Testing types

## Overview

In this article, we’ll write tests that touch on each part of the Rails MVC architecture:

__Model Specs__
Test your Rails models-scopes, validations, custom methods
Sample file: `spec/models/post_spec.rb`

__Controller Specs__
Test your Rails controllers-CRUD, requests, sessions, param shuffling, formats
Sample file: `spec/controllers/posts_controller_spec.rb`

__Feature (a.k.a. Acceptance) Specs__
Test your Rails app from the browser as a user—use cases, interactions, and maybe even some JavaScript
Sample file: `spec/features/post_management_spec.rb`

In addition to those, while not covered here, you might also decide to write unit tests for other parts of your
app: library code, helpers and concerns, routes and views, JavaScript

## Model/unit tests

Model specs are marked by type: :model or if you have set `config.infer_spec_type_from_file_location!` by
placing them in `spec/models`. A model spec is a thin wrapper for an `ActiveSupport::TestCase`, and includes all of the behavior and assertions that it provides, in addition to RSpec's own behavior and expectations.

```ruby
require "rails_helper"

RSpec.describe Post, type: :model do
    context "with 2 or more comments" do
        it "orders them in reverse chronologically" do
            post = Post.create!
            comment1 = post.comments.create!(body: "first comment")
            comment2 = post.comments.create!(body: "second comment")
            expect(post.reload.comments).to eq([comment2, comment1])
        end
    end
end
```

In case you’re not familiar with RSpec’s DSL, we’ll take a quick detour and cover
the three important pieces here:

❖ describe defines a group of examples, as given in the block. It also takes an argument that should indicate what’s being specified. If you’re testing a class or module, you’ll give it that, as shown above. In other cases, you can just pass a string description.

❖ it defines an example and takes a description of the example. You’ll want to phrase the
description in a way that it reads like English (e.g., it "raises an exception when...").

❖ expect makes an expectation (a.k.a. an assertion in other testing frameworks) about its
argument. The expect method takes an object or block and is typically used with to or to_not and a matcher (e.g., `be_valid`, `eq(42)`, or `raise_error`).

In these examples, imagine we’re building an app to compare cars, trucks and SUVs. To that end, we’ll
have a Vehicle model with attributes like the vehicle’s make, model, year and style.

### Testing Validations

Imagine we’d like to require that all vehicles have a year. When users enter new vehicles without a year,
validation should fail and they should be required to enter it in order to continue. To make sure that happens, we’ll need to ensure that our validation in the Vehicle model is working correctly.

```ruby
# spec/models/vehicle_spec.rb
require "rails_helper"

describe Vehicle do
    it "has a valid factory" do
        expect(build(:vehicle)).to be_valid
    end

    it "is invalid without a year" do
        expect(build(:vehicle, year: nil)).to_not be_valid
    end
end
```

### Defining Factories

To make it easier to generate instances of our model in specs, we’ll create factories using the [factory_bot](https://github.com/thoughtbot/factory_bot) gem.

`FactoryBot` is a fixtures replacement with a straightforward definition syntax, support for multiple build strategies (saved instances, unsaved instances, attribute hashes, and stubbed objects), and support for multiple factories for the same class (user, admin_user, and so on), including factory inheritance.

Here’s an example factory definition for our Vehicle model that we’ll use in future examples:

```ruby
# spec/factories/vehicles.rb
FactoryGirl.define do
    factory :vehicle do
        model "Prius"
        make "Toyota"
        year 2014
        style "Car"

        trait :truck do
            model "F-150"
            make "Ford"
            style "Truck"
        end

        trait :suv do
            model "Escalade"
            make "Cadilac"
            style "SUV"
        end
    end
end
```

### Generating Random Data in Your Factories

One approach you might also consider is generating random data in your factories. This has the
advantage of making sure your app works with a broader range of inputs than you’ll be able to think up
manually. The [ffaker](https://github.com/ffaker/ffaker) gem is great for this purpose. In essence, `ffaker` generates fake data for a number of
commonly-used fields like names, phone numbers, addresses, and as it so happens: vehicles. Here’s how
we might re-define the vehicle factory with `ffaker`:

```ruby
# spec/factories/vehicles.rb
FactoryGirl.define do
    factory :vehicle do
        model { Faker::Vehicle.model }
        make { Faker::Vehicle.make }
        year { Faker::Vehicle.year }
        style "Car"
    end
end

# app/models/vehicle.rb
class Vehicle < ActiveRecord::Base
    validates :year, presence: true
end
```

### Testing Methods

Imagine our app will display the average fuel-efficiency (MPG) for each vehicle, based on user-submitted
values. There’s now an MpgSubmission model and a has_many relationship defined in the Vehicle model.
To quickly get the average MPG for a vehicle, we’ll add an average_mpg method to the Vehicle model
that will average the MPG submissions.

```ruby
# spec/models/vehicle_spec.rb
require "rails_helper"
describe Vehicle do
    subject { create(:vehicle) }
    describe "#average_mpg"
        it "returns nil if there are fewer submissions than required"
            9.times.each { create(:mpg_submission, mpg: 25, vehicle: subject) }
            expect(subject.average_mpg).to be_nil
        end

        it "returns the average if there are enough submissions" do
            5.times.each { create(:mpg_submission, mpg: 25, vehicle: subject) }
            5.times.each { create(:mpg_submission, mpg: 30, vehicle: subject) }
            expect(subject.average_mpg).to eq(27.5)
        end
    end
end
```

To make it interesting, we’ll add one additional constraint: if there are fewer than 10 submissions, the method should return nil to indicate insufficient data.

```ruby
# app/models/vehicle.rb
class Vehicle
    has_many :mpg_submissions
    MPG_SUBMISSIONS_NEEDED = 10

    def average_mpg
        if mpg_submissions.count >= MPG_SUBMISSIONS_NEEDED
            mpg_submissions.average(:mpg)
        else
            nil
        end
    end
end
```

## Controller tests

If most of the logic is kept out of your controllers, writing controller specs is easy.
Controller specs test your Rails application at the request level. Here are some of
the questions you should ask when testing controller actions:

❖ If the action should render a view, does it do so?

❖ If the action should redirect to another action, does it do so?

❖ If the action creates, updates, or deletes a resource, does this functionality work?

❖ Specifically, how does creating, updating or deleting items affect the number of records after the request is completed?

❖ If the session and/or cookie should be updated, is that working correctly?

❖ If the request takes or requires certain parameters, what happens if these are missing or invalid?

❖ If access to the action is restricted, does the authentication and authorization logic work as expected?

Here we test the vehicles controller’s create action. We want to ensure that a vehicle is created when the
attributes are valid and not created when the attributes are invalid. Moreover, when the HTML format is
used, the controller should render or redirect the appropriate view.
Testing controller actions ultimately boils down to a few steps:
1. Make a request to the action (get, post, put, delete), with any desired params.
2. If the action should have changed the database in some way, make sure it worked.
3. Make expectations about the response object.
4. If applicable, make expectations about changes to session or cookies.

```ruby
# spec/controllers/vehicles_controller_spec.rb
require "rails_helper"
describe VehiclesController do
    describe "POST #create" do
        context "with valid attributes" do
            it "creates the vehicle" do
                post :create, vehicle: attributes_for(:vehicle)
                expect(Vehicle.count).to eq(1)
            end

            it "redirects to the "show" action for the new vehicle" do
                post :create, vehicle: attributes_for(:vehicle)
                expect(response).to redirect_to Vehicle.first
            end
        end

        context "with invalid attributes" do
            it "does not create the vehicle" do
                post :create, vehicle: attributes_for(:vehicle, year: nil)
                expect(Vehicle.count).to eq(0)
            end

            it "re-renders the "new" view" do
                post :create, vehicle: attributes_for(:vehicle, year: nil)
                expect(response).to render_template :new
            end
        end
    end
end
```

The controller:

```ruby
# app/controllers/vehicles_controller.rb
class VehiclesController < ApplicationController
    def create
        @vehicle = Vehicle.new(vehicle_params)

        respond_to do |format|
            if @vehicle.save
                format.html { redirect_to vehicle_path(@vehicle), notice: "Vehicle was successfully created." }
                format.json { render json: @vehicle, status: :created, location: @vehicle }
            else
                format.html { render action: "new" }
                format.json { render json: @vehicle.errors, status: :unprocessable_entity }
            end
        end
    end
    # of course you"d probably define other actions as well...
end
```

### Testing the JSON Format

You may have noticed that our create action also supports a JSON format. While both formats create a vehicle (or indicate that one could not be created), for the JSON format, it’s important that the right status code is returned so that we don’t confuse API clients. Here’s how we could add a “JSON” context to the above spec:

```ruby
context "with valid attributes" do
    it "creates the vehicle" do
        post :create, vehicle: attributes_for(:vehicle), format: :json
        expect(Vehicle.count).to eq(1)
    end

    it "responds with 201" do
        post :create, vehicle: attributes_for(:vehicle), format: :json
        expect(response).to have_http_status(201)
    end
end
```

## Routing tests
[TODO]

## View tests

View specs are great for testing the conditional display of information in your templates. A lot of developers forget about these tests and use feature specs instead, then wonder why they have a long running test suite. While you can cover each view conditional with a feature spec, I prefer to use view specs like the following

```ruby
# spec/views/products/_product.html.erb_spec.rb
describe "products/_product.html.erb" do
    context "when the product has a url" do
        it "displays the url" do
            assign(:product, build(:product, url: "http://example.com")
            render
            expect(rendered).to have_link "Product", href: "http://example.com"
        end

    end

    context "when the product url is nil" do
        it "displays "None"" do
            assign(:product, build(:product, url: nil)
            render
            expect(rendered).to have_content "None"
        end
    end
end
```

## Feature/integration tests

Feature (a.k.a. Acceptance or Request) specs are a type of integration test. Whereas unit tests are
concerned with individual components, integration tests focus on how they work together. An app with unit
tests but no integration tests is like a pile of individually-tested car parts with no assurance that they can
work together to form a drivable car.
In that sense, feature specs are the place to ensure that all the pieces of your Rails app work together and
achieve the functionality you built it for. In feature specs, we assume the role of the user and play out
various scenarios. For example, some common scenarios in many applications:

❖ A user signs up for a new account.

❖ A user logs into the site.

❖ A user submits a form (with or without all required fields).

❖ An admin manages content.

The following is a basic feature spec for logging into the site. We’ll visit the sign in url, fill in credentials,
and submit the form. To verify that we were successful, we’ll then look for a success message afterwards.

```ruby
# spec/features/user_sign_in_spec.rb
require "rails_helper"

feature "User signs in" do
    scenario "with valid credentials" do
        visit sign_in_path

        fill_in "Username", with: "joe.example"
        fill_in "Password", with: "password"
        click_on "Sign In"

        expect(page).to have_content("You have successfully signed in!")
    end
end
```

### Interacting with JavaScript

The below example, while contrived, demonstrates how expectations can be made about dynamic
content, as well as how JavaScript code can be executed from the feature spec.

```ruby
feature "User views dynamic Hello World message" do
    it "displays Hello World", js: true do
        visit root_path
        
        page.execute_script "document.write("Hello World!")"
        
        expect(page).to have_content("Hello World!")
    end
end
```