package de.larmic.kotlindsl.example.tools

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.larmic.kotlindsl.example.database.CompanyEntity
import de.larmic.kotlindsl.example.database.EmployeeEntity
import de.larmic.kotlindsl.example.rest.CreateCompanyDto
import de.larmic.kotlindsl.example.rest.CreateEmployeeDto

// entities

class EmployeeEntityBuilder {
    var name: String = "Donald Duck"
    var email: String = "donald@duck.de"

    fun build() = EmployeeEntity(name = name, email = email)
}

class CompanyEntityBuilder(private val employees: MutableList<EmployeeEntity> = mutableListOf()) {
    var name: String = "Entenhausen AG"

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

class CreateCompanyDtoBuilder(private val employees: MutableList<CreateEmployeeDto> = mutableListOf()) {
    var name: String = "Entenhausen AG"

    fun employee(block: CreateEmployeeDtoBuilder.() -> Unit) {
        employees.add(CreateEmployeeDtoBuilder().apply(block).build())
    }

    fun buildDto() = CreateCompanyDto(name = name, employees = employees)
    fun buildJson() = jacksonObjectMapper().writeValueAsString(CreateCompanyDto(name = name, employees = employees))
}