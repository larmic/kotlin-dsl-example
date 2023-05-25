package de.larmic.postgres.tools

import de.larmic.postgres.database.CompanyEntity
import de.larmic.postgres.database.EmployeeEntity
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

class EmployeeEntityBuilder {
    var name: String = "Donald Duck"
    var email: String = "donald@duck.de"

    fun build(): EmployeeEntity = EmployeeEntity(name = name, email = email)
}

fun employee(block: EmployeeEntityBuilder.() -> Unit) = EmployeeEntityBuilder().apply(block).build()

class CompanyEntityBuilder {
    var name: String = "Entenhausen AG"
    val employees: MutableList<EmployeeEntity> = mutableListOf()

    fun address(block: EmployeeEntityBuilder.() -> Unit) {
        employees.add(EmployeeEntityBuilder().apply(block).build())
    }

    fun build(): CompanyEntity = CompanyEntity(name = name, employees = employees)
}

fun company(block: CompanyEntityBuilder.() -> Unit) = CompanyEntityBuilder().apply(block).build()