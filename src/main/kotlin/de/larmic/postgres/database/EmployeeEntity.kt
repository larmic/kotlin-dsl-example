package de.larmic.postgres.database

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "employee")
class EmployeeEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_generator")
    @SequenceGenerator(name = "employee_generator", sequenceName = "employee_id_seq", allocationSize = 1)
    val id: Long = 0L,

    @Column(name = "create_date", nullable = false)
    val createDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "last_update_date", nullable = false)
    var lastUpdateDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "email", nullable = false)
    var email: String,
)