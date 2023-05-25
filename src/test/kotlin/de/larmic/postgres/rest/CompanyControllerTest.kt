package de.larmic.postgres.rest

import com.ninjasquad.springmockk.MockkBean
import de.larmic.postgres.database.CompanyRepository
import de.larmic.postgres.database.EmployeeEntity
import de.larmic.postgres.tools.apiTest
import de.larmic.postgres.tools.companyJson
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
        every { companyRepositoryMock.save(any()) } returnsArgument 0

        apiTest("/api/company") {
            post {
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
            }
        }

        this.mockMvc.post("/api/company/") {
            contentType = MediaType.APPLICATION_JSON
            content = companyJson {
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
        }.andExpect {
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