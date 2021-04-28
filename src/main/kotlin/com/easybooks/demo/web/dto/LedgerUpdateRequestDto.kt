package com.easybooks.demo.web.dto

import com.easybooks.demo.LedgerType
import java.time.LocalDate

class LedgerUpdateRequestDto (
    val companyNumber: String,
    val type: LedgerType,
    val date: LocalDate,
    val item: String,
    val unitPrice: Int,
    val quantity: Int,
    val price: Int,
    val VAT: Int,
    val total: Int,
)
