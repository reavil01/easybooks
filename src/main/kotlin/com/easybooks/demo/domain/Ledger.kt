package com.easybooks.demo

import com.easybooks.demo.web.dto.LedgerSaveAndUpdateRequestDto
import org.springframework.data.jpa.repository.Temporal
import java.time.LocalDate
import javax.persistence.*

@Entity
class Ledger(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var companyNumber: String,

    @Column(nullable = false)
    var type: LedgerType,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    var date: LocalDate,

    @Column(nullable = false)
    var item: String,

    var unitPrice: Int,
    var quantity: Int,

    @Column(nullable = false)
    var price: Int,

    @Column(nullable = false)
    var VAT: Int,

    @Column(nullable = false)
    var total: Int,
)

fun Ledger.update(requestDto: LedgerSaveAndUpdateRequestDto) {
    companyNumber = requestDto.companyNumber
    type = requestDto.type
    date = requestDto.date
    item = requestDto.item
    unitPrice = requestDto.unitPrice
    quantity = requestDto.quantity
    price = requestDto.price
    VAT = requestDto.VAT
    total = requestDto.total
}

enum class LedgerType {
    Sell, Purchase
}