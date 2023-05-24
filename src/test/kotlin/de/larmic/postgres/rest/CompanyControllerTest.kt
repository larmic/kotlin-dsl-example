package de.larmic.postgres.rest

import com.ninjasquad.springmockk.MockkBean
import de.larmic.postgres.database.CompanyRepository
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(CompanyController::class)
class CompanyControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var companyRepositoryMock: CompanyRepository

    @Test
    fun `create a new company without employees`() {
        val companyName = "some company"

        every { companyRepositoryMock.save(any()) } returnsArgument 0

        this.mockMvc.post("/api/company/") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "name": "$companyName"
                }
            """.trimIndent()
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { jsonPath("$.name") { value(companyName) } }
            content { jsonPath("$.id") { exists() } }
        }

        verify { companyRepositoryMock.save(withArg {
            assertThat(it.name).isEqualTo(companyName)
            assertThat(it.employees).isEmpty()
        }) }
    }
}