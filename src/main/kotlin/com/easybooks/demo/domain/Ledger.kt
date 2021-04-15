package com.easybooks.demo

import java.util.*
import javax.persistence.*

@Entity
class Ledger(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val clientId: String,

    @Column(nullable = false)
    val date: Date,

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

    @Column(nullable = false)
    val type: LedgerType,
)

enum class LedgerType {
    Sell, Purchase
}