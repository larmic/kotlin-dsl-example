# kotlin-dsl-example

Simple demo showing advantages of using kotlin dsl in spring boot.

If you are looking for a very simple example take a look [here](src/main/kotlin/de/larmic/kotlindsl/example/VerySimpleExample.kt).  
If you are looking for a bit more complex examle take a loot [here](src/test/kotlin/de/larmic/kotlindsl/example/rest/CompanyControllerTest.kt).

## Make goals

```shell
# build application incl. automated tests
$ make java-build-application

# start app in development mode
# postgres db will be started automatically
$ make java-run-application

# send a test rest request and create a company
# incl. some employees
# NOTE: a running server is required (make java-run-application)
$ make http-test-request
```
