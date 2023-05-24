package de.larmic.postgres.rest

import com.ninjasquad.springmockk.MockkBean
import de.larmic.postgres.database.CompanyRepository
import de.larmic.postgres.database.EmployeeEntity
import de.larmic.postgres.tools.createEmployeeDto
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
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
    fun `create a new company`() {
        val companyName = "Entenhausen AG"
        val employee1 = createEmployeeDto(name = "Donald Duck", "donald@duck.de")
        val employee2 = createEmployeeDto(name = "Daniel Düsentrieb", "daniel@düsentrieb.de")

        every { companyRepositoryMock.save(any()) } returnsArgument 0

        this.mockMvc.post("/api/company/") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "name": "$companyName",
                    "employees": [
                        {
                            "name": "${employee1.name}",
                            "email": "${employee1.email}"
                        },
                        {
                            "name": "${employee2.name}",
                            "email": "${employee2.email}"
                        }
                    ]
                }
            """.trimIndent()
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { jsonPath("$.id") { exists() } }
            content { jsonPath("$.name") { value(companyName) } }
            content { jsonPath("$.employees[0].id") { exists() } }
            content { jsonPath("$.employees[0].name") { value(employee1.name) } }
            content { jsonPath("$.employees[0].email") { value(employee1.email) } }
            content { jsonPath("$.employees[1].id") { exists() } }
            content { jsonPath("$.employees[1].name") { value(employee2.name) } }
            content { jsonPath("$.employees[1].email") { value(employee2.email) } }
        }

        verify {
            companyRepositoryMock.save(withArg {
                assertThat(it.name).isEqualTo(companyName)
                assertThat(it.employees)
                    .extracting(EmployeeEntity::name, EmployeeEntity::email)
                    .containsExactlyInAnyOrder(
                        tuple(employee1.name, employee1.email),
                        tuple(employee2.name, employee2.email),
                    )
            })
        }
    }
}