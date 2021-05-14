package com.easybooks.demo.domain

import com.easybooks.demo.web.ledger.dto.LedgerSaveAndUpdateRequestDto
import org.springframework.data.jpa.repository.Temporal
import java.time.LocalDate
import javax.persistence.*

@Entity
class Ledger(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(nullable = false)
    var company: Company,

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
    var vat: Int,

    @Column(nullable = false)
    var total: Int,
)

fun Ledger.update(requestDto: LedgerSaveAndUpdateRequestDto) {
    requestDto.type.also { type = it }
    requestDto.date.also { date = it }
    requestDto.item.also { item = it }
    requestDto.unitPrice.also { unitPrice = it }
    requestDto.quantity.also { quantity = it }
    requestDto.price.also { price = it }
    requestDto.vat.also { vat = it }
    requestDto.total.also { total = it }
}

enum class LedgerType {
    Sell, Purchase
}