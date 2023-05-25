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