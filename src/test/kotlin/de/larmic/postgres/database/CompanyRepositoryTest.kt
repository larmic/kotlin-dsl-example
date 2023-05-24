package de.larmic.postgres.database

import de.larmic.postgres.testcontainers.AbstractDatabaseTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.jupiter.api.Test
import java.time.LocalDateTime.now
import java.time.temporal.ChronoUnit

class CompanyRepositoryTest : AbstractDatabaseTest() {

    @Test
    fun save() {
        val entity = CompanyEntity(name = "Some company")

        companyRepository.save(entity)

        assertThat(entity.id).isNotZero
        assertThat(entity.name).isEqualTo("Some company")
        assertThat(entity.createDate).isCloseTo(now(), within(1, ChronoUnit.SECONDS))
        assertThat(entity.lastUpdateDate).isCloseTo(now(), within(1, ChronoUnit.SECONDS))
    }

}