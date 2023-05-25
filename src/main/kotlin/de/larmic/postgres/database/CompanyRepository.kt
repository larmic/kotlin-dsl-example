package de.larmic.postgres.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
class CompanyRepository(private val companyJpaRepository: CompanyJpaRepository) {

    fun save(company: CompanyEntity) = companyJpaRepository.save(company)

    fun get(companyId: Long) = companyJpaRepository.getReferenceById(companyId)

    infix fun exists(companyId: Long) = companyJpaRepository.existsById(companyId)

}

@Repository
interface CompanyJpaRepository : JpaRepository<CompanyEntity, Long>