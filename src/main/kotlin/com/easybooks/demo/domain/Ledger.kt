package com.easybooks.demo

import org.springframework.data.jpa.repository.Temporal
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
class Ledger(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val companyId: String,

    @Column(nullable = false)
    val type: LedgerType,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val date: LocalDate,

    @Column(nullable = false)
    val item: String,

    val unitPrice: Int,
    val quantity: Int,

    @Column(nullable = false)
    val price: Int,

    @Column(nullable = false)
    val VAT: Int,

    @Column(nullable = false)
    val total: Int,
)

enum class LedgerType {
    Sell, Purchase
}