package de.larmic.postgres.rest

import de.larmic.postgres.database.CompanyEntity
import de.larmic.postgres.database.CompanyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class CompanyController(private val companyRepository: CompanyRepository) {

    @PostMapping("/")
    fun create(@RequestBody name: String) = companyRepository.save(CompanyEntity(name = name)).mapToDto()

    @GetMapping("/{id}")
    fun readTweet(@PathVariable id: Long): ResponseEntity<CompanyDto> {
        val company = companyRepository.findByIdOrNull(id) ?: return ResponseEntity.notFound().build()
        return company.wrapInResponse()
    }

    @GetMapping("/")
    fun readAllTweets() = companyRepository.findAll().map { it.mapToDto() }

    private fun CompanyEntity.mapToDto() = CompanyDto(this.id.toString(), this.name)
    private fun CompanyEntity.wrapInResponse() = ResponseEntity.ok(this.mapToDto())
}