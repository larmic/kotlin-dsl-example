package de.larmic.postgres.database

import org.springframework.stereotype.Repository

@Repository
class CompanyRepository(private val companyJpaRepository: CompanyJpaRepository) {

    fun save(company: CompanyEntity) = companyJpaRepository.save(company)

    fun get(companyId: Long) = companyJpaRepository.getReferenceById(companyId)

    fun exist(companyId: Long) = companyJpaRepository.existsById(companyId)

}