package de.larmic.postgres.tools

import de.larmic.postgres.database.EmployeeEntity
import de.larmic.postgres.rest.CreateEmployeeDto

fun createEmployeeEntity(
    name: String = "Donald Duck",
    email: String = "donald@duck.de",
) = EmployeeEntity(name = name, email = email)

fun createEmployeeDto(
    name: String = "Donald Duck",
    email: String = "donald@duck.de",
) = CreateEmployeeDto(name = name, email = email)