# Misc
.DEFAULT_GOAL = help

help: ## Outputs this help screen
	@grep -E '(^[a-zA-Z0-9_-]+:.*?##.*$$)|(^##)' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}{printf "\033[32m%-30s\033[0m %s\n", $$1, $$2}' | sed -e 's/\[32m##/[33m/'

## —— Build 🏗️———————————————————————————————————————————————————————————————————————
java-build-application: ## Builds application including automated tests
	./mvnw clean package

## —— Run 🏃🏽————————————————————————————————————————————————————————————————————————
java-run-application: ## Starts app in development mode (postgres db will be started automatically)
	./mvnw spring-boot:test-run

## —— Test 👀—————————————————————————————————————————————————————————————————————————
http-test-request: ## Sends a test rest request and create a company | NOTE: a running server is required (make java-run-application)
	docker run --rm -i -t -v ./:/workdir jetbrains/intellij-http-client:231.9011.14 \
      -L VERBOSE \
      -e docker \
      -v ./http-client.env.json \
      ./requests.http