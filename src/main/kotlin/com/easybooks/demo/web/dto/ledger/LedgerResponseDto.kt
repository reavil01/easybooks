package com.easybooks.demo.web.dto.ledger

import com.easybooks.demo.domain.Ledger

class LedgerResponseDto(ledger: Ledger) {
    val id = ledger.id
    val company = ledger.company
    val type = ledger.type
    val date = ledger.date
    val item = ledger.item
    val unitPrice = ledger.unitPrice
    val quantity = ledger.quantity
    val price = ledger.price
    val vat = ledger.vat
    val total = ledger.total
}
