package ru.service.test.payment.entities

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "accounts")
class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long? = null

    @Column(name = "balance", precision = 10, scale = 2, nullable = false,
            columnDefinition="DECIMAL(10,2)")
    var balance: BigDecimal = BigDecimal(0.00)

}