package de.larmic.postgres.rest

import de.larmic.postgres.database.CompanyEntity
import de.larmic.postgres.database.CompanyRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/company")
class CompanyController(private val companyRepository: CompanyRepository) {

    @PostMapping("/", consumes = ["application/json"], produces = ["application/json"])
    fun create(@RequestBody dto: CreateCompanyDto) = dto
            .mapToEntity()
            .storyInDatabase()
            .mapToDto()

    @GetMapping("/{id}")
    fun readTweet(@PathVariable id: Long): ResponseEntity<ReadCompanyDto> {
        if (companyRepository.exist(id)) {
            return companyRepository.get(id).wrapInResponse()
        }

        return ResponseEntity.notFound().build()
    }

    private fun CreateCompanyDto.mapToEntity() = CompanyEntity(name = this.name)
    private fun CompanyEntity.mapToDto() = ReadCompanyDto(this.id.toString(), this.name)
    private fun CompanyEntity.wrapInResponse() = ResponseEntity.ok(this.mapToDto())
    private fun CompanyEntity.storyInDatabase() = companyRepository.save(this)
}

class CreateCompanyDto(val name: String)
class ReadCompanyDto(val id: String, val name: String)