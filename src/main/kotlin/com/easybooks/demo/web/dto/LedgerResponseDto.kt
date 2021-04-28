package com.easybooks.demo.web.dto

import com.easybooks.demo.Ledger

class LedgerResponseDto(ledger: Ledger) {
    val companyNumber = ledger.companyNumber
    val type = ledger.type
    val date = ledger.date
    val item = ledger.item
    val unitPrice = ledger.unitPrice
    val quantity = ledger.quantity
    val price = ledger.price
    val VAT = ledger.VAT
    val total = ledger.total
}