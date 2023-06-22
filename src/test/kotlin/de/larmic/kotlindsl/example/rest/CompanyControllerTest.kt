package de.larmic.kotlindsl.example.rest

import com.ninjasquad.springmockk.MockkBean
import de.larmic.kotlindsl.example.database.CompanyRepository
import de.larmic.kotlindsl.example.database.EmployeeEntity
import de.larmic.kotlindsl.example.tools.apiTest
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(CompanyController::class)
class CompanyControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var companyRepositoryMock: CompanyRepository

    @Test
    fun `create a new company`() {
        apiTest(mockMvc, "/api/company/") {
            post {
                prepare {
                    every { companyRepositoryMock.save(any()) } returnsArgument 0
                }
                body {
                    name = "Panzerknacker AG"
                    employee {
                        name = "Karlchen Knack"
                        email = "karlchen@knack.de"
                    }
                    employee {
                        name = "Kuno Knack"
                        email = "kuno@knack.de"
                    }
                }
                expectedResult {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    content { jsonPath("$.id") { exists() } }
                    content { jsonPath("$.name") { value("Panzerknacker AG") } }
                    content { jsonPath("$.employees[0].id") { exists() } }
                    content { jsonPath("$.employees[0].name") { value("Karlchen Knack") } }
                    content { jsonPath("$.employees[0].email") { value("karlchen@knack.de") } }
                    content { jsonPath("$.employees[1].id") { exists() } }
                    content { jsonPath("$.employees[1].name") { value("Kuno Knack") } }
                    content { jsonPath("$.employees[1].email") { value("kuno@knack.de") } }
                }
            }
        }

        verify {
            companyRepositoryMock.save(withArg {
                assertThat(it.name).isEqualTo("Panzerknacker AG")
                assertThat(it.employees)
                    .extracting(EmployeeEntity::name, EmployeeEntity::email)
                    .containsExactlyInAnyOrder(
                        tuple("Karlchen Knack", "karlchen@knack.de"),
                        tuple("Kuno Knack", "kuno@knack.de"),
                    )
            })
        }
    }
}