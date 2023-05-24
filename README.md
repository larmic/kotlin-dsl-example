# kotlin-dsl-example

Simple demo showing advantages of using kotlin dsl in spring boot.

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
