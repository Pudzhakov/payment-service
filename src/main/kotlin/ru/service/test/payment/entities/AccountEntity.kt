package ru.service.test.payment.entities

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "accounts")
class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null

    @Column(name = "balance", precision = 10, scale = 2, nullable = false,
            columnDefinition="DECIMAL(10,2)")
    var balance: BigDecimal = BigDecimal(0.00)

    override fun equals(other: Any?): Boolean {

        if (this === other)
            return true

        if (other == null || javaClass != other.javaClass)
            return false

        return id?.equals((other as AccountEntity).id) ?: false

    }

    override fun hashCode(): Int { return 31 }

}