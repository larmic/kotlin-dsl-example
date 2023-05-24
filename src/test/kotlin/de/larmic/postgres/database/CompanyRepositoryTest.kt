package de.larmic.postgres.database

import de.larmic.postgres.tools.createEmployeeEntity
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.assertj.core.groups.Tuple.tuple
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDateTime.now
import java.time.temporal.ChronoUnit

@Transactional(propagation = Propagation.NEVER)
@DataJpaTest
@Testcontainers
@ActiveProfiles("database")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(CompanyRepository::class)
class CompanyRepositoryTest {

    @Autowired
    protected lateinit var companyJpaRepository: CompanyJpaRepository

    @Autowired
    protected lateinit var companyRepository: CompanyRepository

    @BeforeEach
    fun setUp() {
        companyJpaRepository.deleteAll()
    }

    @Test
    fun `save company without employees`() {
        val company = CompanyEntity(name = "Entenhausen AG")

        companyRepository.save(company)

        val fetchedCompany = companyRepository.get(companyId = company.id)

        assertThat(fetchedCompany.id).isNotZero
        assertThat(fetchedCompany.name).isEqualTo(company.name)
        assertThat(fetchedCompany.employees).isEmpty()
        assertThat(fetchedCompany.createDate).isCloseTo(now(), within(1, ChronoUnit.SECONDS))
        assertThat(fetchedCompany.lastUpdateDate).isCloseTo(now(), within(1, ChronoUnit.SECONDS))
    }

    @Test
    fun `save company with employees`() {
        val employee1 = createEmployeeEntity(name = "Donald Duck", "donald@duck.de")
        val employee2 = createEmployeeEntity(name = "Daniel Düsentrieb", "daniel@düsentrieb.de")
        val company = CompanyEntity(name = "Entenhausen AG", employees = mutableListOf(employee1, employee2))

        companyRepository.save(company)

        val fetchedCompany = companyRepository.get(companyId = company.id)

        assertThat(fetchedCompany.id).isNotZero
        assertThat(fetchedCompany.name).isEqualTo(company.name)
        assertThat(fetchedCompany.employees)
            .extracting(EmployeeEntity::name, EmployeeEntity::email)
            .containsExactlyInAnyOrder(
                tuple(employee1.name, employee1.email),
                tuple(employee2.name, employee2.email),
            )
        assertThat(fetchedCompany.createDate).isCloseTo(now(), within(1, ChronoUnit.SECONDS))
        assertThat(fetchedCompany.lastUpdateDate).isCloseTo(now(), within(1, ChronoUnit.SECONDS))
    }

    companion object {
        @Container
        @ServiceConnection
        val postgres = PostgreSQLContainer("postgres:15.3-alpine")
    }
}