package com.easybooks.demo.web.dto.ledger

import com.easybooks.demo.domain.Company
import com.easybooks.demo.domain.Ledger
import com.easybooks.demo.domain.LedgerType
import java.time.LocalDate

class LedgerSaveAndUpdateRequestDto (
    val companyNumber: String,
    val type: LedgerType,
    val date: LocalDate,
    val item: String,
    val unitPrice: Int,
    val quantity: Int,
    val price: Int,
    val vat: Int,
    val total: Int,
)

fun LedgerSaveAndUpdateRequestDto.toEntity(company: Company): Ledger {
    return Ledger(
        id = 0,
        company = company,
        type = type,
        date = date,
        item = item,
        unitPrice = unitPrice,
        quantity = quantity,
        price = price,
        vat = vat,
        total = total
    )
}