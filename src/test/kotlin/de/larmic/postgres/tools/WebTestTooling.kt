package de.larmic.postgres.tools

class ApiTestBuilder(contextPath: String) {
    fun execute() {}
}

fun apiTest(contextPath: String, block: ApiTestBuilder.() -> Unit) = ApiTestBuilder(contextPath).execute()