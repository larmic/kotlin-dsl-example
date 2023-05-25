package de.larmic.postgres.tools

class PostBuilder {

    private var body: String = ""

    fun body(block: CreateCompanyDtoBuilder.() -> Unit) {
        this.body = CreateCompanyDtoBuilder().apply(block).buildJson()
    }

    fun buildJson() = body
}

class ApiTestBuilder(private val contextPath: String) {

    private var body: String = ""

    fun post(block: PostBuilder.() -> Unit) {
        this.body = PostBuilder().apply(block).buildJson()
    }

    fun execute() {
        println("POST '$contextPath'")
    }
}

fun apiTest(contextPath: String, block: ApiTestBuilder.() -> Unit) = ApiTestBuilder(contextPath).apply(block).execute()