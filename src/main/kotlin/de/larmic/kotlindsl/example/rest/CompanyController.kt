package de.larmic.kotlindsl.example.rest

import de.larmic.kotlindsl.example.database.CompanyEntity
import de.larmic.kotlindsl.example.database.CompanyRepository
import de.larmic.kotlindsl.example.database.EmployeeEntity
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
        if (companyRepository exists id) {
            return companyRepository.get(id).wrapInResponse()
        }

        return ResponseEntity.notFound().build()
    }

    private fun CompanyEntity.storyInDatabase() = companyRepository.save(this)
}

// dtos
class CreateEmployeeDto(val name: String, val email: String)
class ReadEmployeeDto(val id: Long, val name: String, val email: String)

class CreateCompanyDto(val name: String, val employees: List<CreateEmployeeDto> = emptyList())
class ReadCompanyDto(val id: Long, val name: String, val employees: List<ReadEmployeeDto> = emptyList())

// mapping stuff
private fun CreateEmployeeDto.mapToEntity() = EmployeeEntity(name = this.name, email = this.email)
private fun CreateCompanyDto.mapToEntity() =
    CompanyEntity(name = this.name, employees = this.employees.map { it.mapToEntity() }.toMutableList())
private fun CompanyEntity.mapToDto() = ReadCompanyDto(id = this.id, name = this.name, employees = this.employees.map { it.mapToDto() })
private fun CompanyEntity.wrapInResponse() = ResponseEntity.ok(this.mapToDto())
private fun EmployeeEntity.mapToDto() = ReadEmployeeDto(id = this.id, name = this.name, email = this.email)