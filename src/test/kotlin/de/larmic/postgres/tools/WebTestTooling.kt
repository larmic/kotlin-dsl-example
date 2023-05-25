package de.larmic.postgres.tools

import de.larmic.postgres.rest.CreateCompanyDto

class PostBuilder {

    var body: CreateCompanyDto = companyDto {}

}

class ApiTestBuilder(contextPath: String) {

    fun post(block: PostBuilder.() -> Unit) {

    }

    fun execute() {}
}

fun apiTest(contextPath: String, block: ApiTestBuilder.() -> Unit) = ApiTestBuilder(contextPath).execute()