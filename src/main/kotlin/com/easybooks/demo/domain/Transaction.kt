package com.easybooks.demo.domain

import com.easybooks.demo.web.transaction.dto.TransactionSaveAndUpdateDto
import java.time.LocalDate
import javax.persistence.*

@Entity
class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @ManyToOne
    @JoinColumn(nullable = false)
    var company: Company,

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

fun Transaction.update(requestDto: TransactionSaveAndUpdateDto) {
    date = requestDto.date
    price = requestDto.price
    type = requestDto.type
}