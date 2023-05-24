package de.larmic.postgres.database

import de.larmic.postgres.testcontainers.AbstractDatabaseTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.assertj.core.groups.Tuple.tuple
import org.junit.jupiter.api.Test
import java.time.LocalDateTime.now
import java.time.temporal.ChronoUnit

class CompanyRepositoryTest : AbstractDatabaseTest() {

    @Test
    fun `save company without employees`() {
        val entity = CompanyEntity(name = "Some company")

        companyRepository.save(entity)

        assertThat(entity.id).isNotZero
        assertThat(entity.name).isEqualTo("Some company")
        assertThat(entity.employees).isEmpty()
        assertThat(entity.createDate).isCloseTo(now(), within(1, ChronoUnit.SECONDS))
        assertThat(entity.lastUpdateDate).isCloseTo(now(), within(1, ChronoUnit.SECONDS))
    }

    @Test
    fun `save company with employees`() {
        val entity = CompanyEntity(name = "Some company")
        entity.employees.add(EmployeeEntity(name = "Donald Duck", email = "donald@duck.de"))

        companyRepository.save(entity)

        assertThat(entity.id).isNotZero
        assertThat(entity.name).isEqualTo("Some company")
        assertThat(entity.employees)
            .extracting(EmployeeEntity::name, EmployeeEntity::email)
            .containsExactly(
                tuple("Donald Duck", "donald@duck.de")
            )
        assertThat(entity.createDate).isCloseTo(now(), within(1, ChronoUnit.SECONDS))
        assertThat(entity.lastUpdateDate).isCloseTo(now(), within(1, ChronoUnit.SECONDS))
    }

}