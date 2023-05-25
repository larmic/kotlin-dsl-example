package de.larmic.postgres.tools

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.post

class PostBuilder {

    private var body: String = ""

    fun body(block: CreateCompanyDtoBuilder.() -> Unit) {
        this.body = CreateCompanyDtoBuilder().apply(block).buildJson()
    }

    fun buildJson() = body
}

class ApiTestBuilder(private val mockMvc: MockMvc, private val contextPath: String) {

    private var body: String = ""
    private var preparation: (Any.() -> Unit)? = null

    fun prepare(block: Any.() -> Unit) {
        this.preparation = block
    }

    fun post(block: PostBuilder.() -> Unit) {
        this.body = PostBuilder().apply(block).buildJson()
    }

    fun execute(): ResultActionsDsl {
        preparation?.invoke {}
        return mockMvc.post(contextPath) {
            contentType = MediaType.APPLICATION_JSON
            content = body
        }
    }
}

fun apiTest(mockMvc: MockMvc, contextPath: String, block: ApiTestBuilder.() -> Unit) = ApiTestBuilder(mockMvc, contextPath).apply(block).execute()