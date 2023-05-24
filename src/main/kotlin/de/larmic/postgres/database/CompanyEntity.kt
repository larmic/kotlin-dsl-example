package de.larmic.postgres.database

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "company")
class CompanyEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_generator")
    @SequenceGenerator(name = "company_generator", sequenceName = "company_id_seq", allocationSize = 1)
    val id: Long = 0L,

    @Column(name = "create_date", nullable = false)
    val createDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "last_update_date", nullable = false)
    var lastUpdateDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "name", nullable = false)
    var name: String,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "company_fk")
    val employees: MutableList<EmployeeEntity> = mutableListOf()
)