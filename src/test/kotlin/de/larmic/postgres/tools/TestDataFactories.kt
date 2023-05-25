package de.larmic.postgres.tools

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.larmic.postgres.database.CompanyEntity
import de.larmic.postgres.database.EmployeeEntity
import de.larmic.postgres.rest.CreateCompanyDto
import de.larmic.postgres.rest.CreateEmployeeDto

fun createEmployeeEntity(
    name: String = "Donald Duck",
    email: String = "donald@duck.de",
) = EmployeeEntity(name = name, email = email)

fun createCompanyEntity(
    name: String = "Entenhausen AG",
    employees: List<EmployeeEntity> = emptyList(),
) = CompanyEntity(name = name, employees = employees.toMutableList())

fun createEmployeeDto(
    name: String = "Donald Duck",
    email: String = "donald@duck.de",
) = CreateEmployeeDto(name = name, email = email)

// entities

class EmployeeEntityBuilder {
    var name: String = "Donald Duck"
    var email: String = "donald@duck.de"

    fun build() = EmployeeEntity(name = name, email = email)
}

fun employee(block: EmployeeEntityBuilder.() -> Unit) = EmployeeEntityBuilder().apply(block).build()

class CompanyEntityBuilder {
    var name: String = "Entenhausen AG"
    val employees: MutableList<EmployeeEntity> = mutableListOf()

    fun employee(block: EmployeeEntityBuilder.() -> Unit) {
        employees.add(EmployeeEntityBuilder().apply(block).build())
    }

    fun build() = CompanyEntity(name = name, employees = employees)
}

fun company(block: CompanyEntityBuilder.() -> Unit) = CompanyEntityBuilder().apply(block).build()

// dtos

class CreateEmployeeDtoBuilder {
    var name: String = "Donald Duck"
    var email: String = "donald@duck.de"

    fun build() = CreateEmployeeDto(name = name, email = email)
}

fun employeeDto(block: CreateEmployeeDtoBuilder.() -> Unit) = CreateEmployeeDtoBuilder().apply(block).build()

class CreateCompanyDtoBuilder {
    var name: String = "Entenhausen AG"
    val employees: MutableList<CreateEmployeeDto> = mutableListOf()

    fun employee(block: CreateEmployeeDtoBuilder.() -> Unit) {
        employees.add(CreateEmployeeDtoBuilder().apply(block).build())
    }

    fun build() = jacksonObjectMapper().writeValueAsString(CreateCompanyDto(name = name, employees = employees))
}

fun companyDto(block: CreateCompanyDtoBuilder.() -> Unit) = CreateCompanyDtoBuilder().apply(block).build()