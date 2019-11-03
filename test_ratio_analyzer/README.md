# Test Ratio Analyzer

This small tool analyzes a github repository and calculates the test to production code ratio.

## Build

```
lein uberjar
```

## Usage

```
java -jar analyzer.jar https://github.com/bakku/annoyme ruby
```

The following languages are supported:

* Ruby
* Clojure
* Java
* Haskell
* Go
* Python
