package com.easybooks.demo.web.dto

import com.easybooks.demo.Ledger
import com.easybooks.demo.LedgerType
import java.time.LocalDate

class LedgerSaveRequestDto (
    val companyId: String,
    val type: LedgerType,
    val date: LocalDate,
    val item: String,
    val unitPrice: Int,
    val quantity: Int,
    val price: Int,
    val VAT: Int,
    val total: Int,
)

fun LedgerSaveRequestDto.toEntity() = Ledger(
    id = 0,
    companyId = companyId,
    type = type,
    date = date,
    item = item,
    unitPrice = unitPrice,
    quantity = quantity,
    price = price,
    VAT = VAT,
    total = total
)