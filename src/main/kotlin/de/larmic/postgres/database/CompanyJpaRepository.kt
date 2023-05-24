package de.larmic.postgres.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyJpaRepository : JpaRepository<CompanyEntity, Long>