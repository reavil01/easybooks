package com.easybooks.demo.domain

import java.time.LocalDate
import javax.persistence.*

@Entity
class Transaction (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var companyNumber: String,

    @Column(nullable = false)
    var date: LocalDate,

    @Column(nullable = false)
    var price: Int,

    @Column(nullable = false)
    @Enumerated
    var type: TransactionType
)

enum class TransactionType {
    Deposit, Withdraw
}