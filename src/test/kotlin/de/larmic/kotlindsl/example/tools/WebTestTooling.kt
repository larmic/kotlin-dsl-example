package de.larmic.kotlindsl.example.tools

import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.post

class PostBuilder {

    var body: String = ""
        private set

    var dsl: (MockMvcResultMatchersDsl.() -> Unit)? = null
        private set

    fun body(block: CreateCompanyDtoBuilder.() -> Unit) {
        this.body = CreateCompanyDtoBuilder().apply(block).buildJson()
    }

    fun expectedResult(dsl: MockMvcResultMatchersDsl.() -> Unit) {
        this.dsl = dsl
    }
}

class ApiTestBuilder(private val mockMvc: MockMvc, private val contextPath: String) {

    private var postBuilder: PostBuilder? = null
    private var preparation: (Any.() -> Unit)? = null

    fun prepare(block: Any.() -> Unit) {
        this.preparation = block
    }

    fun post(block: PostBuilder.() -> Unit) {
        this.postBuilder = PostBuilder().apply(block)
    }

    fun execute(): ResultActionsDsl? {
        preparation?.invoke {}
        postBuilder?.let {
            val post = mockMvc.post(contextPath) {
                contentType = MediaType.APPLICATION_JSON
                content = it.body
            }

            if (it.dsl != null) {
                post.andExpect(it.dsl!!)
            }

            return post
        }
        return null
    }
}

fun apiTest(mockMvc: MockMvc, contextPath: String, block: ApiTestBuilder.() -> Unit) = ApiTestBuilder(mockMvc, contextPath).apply(block).execute()