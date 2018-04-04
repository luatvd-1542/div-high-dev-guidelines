## Some guide to choosing the best Gems for your Ruby project

  * Where do we go to find the Gem?
    * [The Ruby Toolbox](): That is a great way to get a list of the gems in a category(along with: how  many people use it?, how often is it updated?)
  * The stats:
    * Numbers of download
    * Last release
    * Latest Commit
  * Code and Document:
    * Doc quality?(1-5, 5 best)
    * Test Quality?(1-5, 5 best)
    * Issues/PRs
    * Actively Maintained?
    * Recommended by others?
    * Good code?

## Recommended Ruby Gems when start new project
  * Authentication
    * [Devise](https://github.com/plataformatec/devise) : This is a flexible authentication solution for Rails, easy to setup, easy to override and extend

  * Authorization
    * [Cancan](https://github.com/CanCanCommunity/cancancan): Cancan is an authorization library for Ruby on Rails. Easy way to define and access user permission, it’s also give us simple way to authorize controller action and handle authorization exception.
    * [Rolify](https://github.com/RolifyCommunity/rolify): Role management Library with resource scoping.


  * Omniauth
    * [omniauth-facebook](https://github.com/mkdynamic/omniauth-facebook)
    * [omniauth-google-oauth2](https://github.com/zquestz/omniauth-google-oauth2)
    * [omniauth-twitter](https://github.com/arunagw/omniauth-twitter)
    * [omniauth-github](https://github.com/omniauth/omniauth-github)
    * [omniauth-linkedin-oauth2](https://github.com/decioferreira/omniauth-linkedin-oauth2)
    * [omniauth-weibo-oauth2](https://github.com/beenhero/omniauth-weibo-oauth2)

  * API
    * [Grape](https://github.com/ruby-grape/grape): Microframework to create REST-full APIs in Ruby
    * [ActiveModel::Serializers](https://github.com/rails-api/active_model_serializers): Serializer brings convention over configuration to your JSON generation.

  * File uploading
    * [Carrierwave](https://github.com/carrierwaveuploader/carrierwave): This gem provides a simple and extremely flexible way to upload files from Ruby applications
    * [PaperClip](https://github.com/thoughtbot/paperclip): Paperclip is intended as an easy file attachment library for ActiveRecord
    * [aws-sdk](https://github.com/aws/aws-sdk-ruby): Combined with PaperClip or Carrierway will allow you to upload assets such as images through your application to AWS S3

  * Mailer
      * [letter-opener](https://github.com/ryanb/letter_opener): Preview email in the default browser instead of sending it.
      * [mailcatcher](https://github.com/sj26/mailcatcher): MailCatcher runs a simple SMTP server which catches any message sent to it to display in a web interface

  * Searching
    * [Ransack](https://github.com/activerecord-hackery/ransack): Ransack enables the creation of both simple and advanced search forms for your Ruby on Rails application
    * [Elasticsearch-rails](https://github.com/elastic/elasticsearch-rails): Elasticsearch integrations for ActiveModel/Record and Ruby on Rails.
    * [Chewy](https://github.com/toptal/chewy): High-level Elasticsearch Ruby framework based on the official elasticsearch-ruby client
    * [SearchKick](https://github.com/ankane/searchkick): Intelligent search made easy with Rails and Elasticsearch.

  * Admin panel
    * [Active Admin](https://github.com/activeadmin/activeadmin): A Ruby on Rails framework for creating elegant backends for website administration
    * [Rails Admin](https://github.com/sferik/rails_admin): A Rails engine that provides an easy-to-use interface for managing your data.

  * Testing
    * [rspec-rails](https://github.com/rspec/rspec-rails): A testing framework for Rails 3.x, 4.x and 5.0.
    * [factory_factory_girl](https://github.com/st0012/factory_factory_girl): A really useful gem that lets us generate test data more efficiently
    * [simplecov](https://github.com/colszowka/simplecov): SimpleCov is a code coverage analysis tool for Ruby
    * [vcr](https://github.com/vcr/vcr): Record your test suite's HTTP interactions and replay them during future test runs for fast, deterministic, accurate tests.
    * [capybara](https://github.com/teamcapybara/capybara): Capybara helps you test web applications by simulating how a real user would interact with your app

  * Schedule
    * [Whenever](https://github.com/javan/whenever): Whenever is a Ruby gem that provides a clear syntax for writing and deploying cron jobs.
    * [Sidekiq](https://github.com/mperham/sidekiq): Sidekiq uses threads to handle many jobs at the same time in the same process. It does not require Rails but will integrate tightly with Rails to make background processing dead simple.
    * [Delayed Job](https://github.com/collectiveidea/delayed_job): Database based asynchronous priority queue system.

  * View Helper
    * [Simple Form](https://github.com/plataformatec/simple_form):  A flexible as possible while helping you with powerful components to create your forms
    * [Cocoon](https://github.com/nathanvda/cocoon): Dynamic nested form

  * Environments Variables
    * [Dotenv](https://github.com/bkeepers/dotenv): Allow set environment variable in .env file and loading it into ENV
    * [Figaro](https://github.com/laserlemon/figaro): Configuration values often include sensitive information. Figaro strives to be secure       by default by encouraging a convention that keeps configuration out of Git.

  * Deployment
    * [Capistrano](https://github.com/capistrano/rails)

  * Debug
    * [pry-rails](https://github.com/rweng/pry-rails): Avoid repeating yourself, use pry-rails instead of copying the initializer to every rails project. This is a small gem which causes rails console to open pry. It therefore depends on pry.
    * [byebug](https://github.com/deivid-rodriguez/byebug): Byebug is a simple to use, feature rich debugger for Ruby. It uses the TracePoint API for execution control and the Debug Inspector API for call stack navigation

  * Application performance
    * [Bullet](https://github.com/flyerhzm/bullet): The Bullet gem is designed to help you increase your application's performance by reducing the number of queries
    * [Rack mini profiler](https://github.com/MiniProfiler/rack-mini-profiler): Middleware that displays speed badge for every html page. Designed to work both in production and in development.

  * Security
    * [brakeman](https://github.com/presidentbeef/brakeman): Brakeman is an open source static analysis tool which checks Ruby on Rails applications for security vulnerabilities
    *[bundle-audit](https://github.com/rubysec/bundler-audit): Checks for vulnerable versions of gems

  * Coding style
    * [Rubocop](https://github.com/bbatsov/rubocop): A Ruby static code analyzer. Out of the box it will enforce many of the guidelines outlined in the community ruby style guide.
    * [Rails best practice](https://github.com/flyerhzm/rails_best_practices): A code metric tool to check the quality of Rails code.

  * Some others useful gems
    * [kaminari](https://github.com/kaminari/kaminari): A Scope & Engine based, clean, powerful, customizable and sophisticated paginator for modern web app frameworks and ORMs
    * [will_paginate](https://github.com/mislav/will_paginate): Good choice when it comes to pagination, it can get you up and running with pagination in a few line of code
    * [Nokogiri](https://github.com/sparklemotion/nokogiri): Nokogiri (鋸) is an HTML, XML, SAX, and Reader parser. Among Nokogiri's many features is the ability to search documents via XPath or CSS3 selectors.
    * [Ckeditor](https://github.com/galetahub/ckeditor): A WYSIWYG text editor designed to simplify web content creation
    * [spreadsheet](https://github.com/zdavatz/spreadsheet): It is designed to read and write Spreadsheet Documents
    * [bootstrap-sass](https://github.com/twbs/bootstrap-sass): Allow to use the Bootstrap HTML/CSS framework to quick build a usable interface for the application
    * [Friend ID](https://github.com/norman/friendly_id): Auto increment primary ID used in rails objects.
    * [lodash-rails](https://github.com/rh/lodash-rails): Lodash is very helpful in adding enumerable function into javascript and in most case of Rails Application.
