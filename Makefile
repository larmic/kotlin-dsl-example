java-build-application:
	./mvnw clean package

java-run-application:
	./mvnw spring-boot:test-run

http-test-request:
	docker run --rm -i -t -v ./:/workdir jetbrains/intellij-http-client:231.9011.14 \
      -L VERBOSE \
      -e docker \
      -v ./http-client.env.json \
      ./requests.http