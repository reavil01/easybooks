package com.easybooks.demo.web.transaction.dto

import com.easybooks.demo.domain.Company
import com.easybooks.demo.domain.Transaction
import com.easybooks.demo.domain.TransactionType
import java.time.LocalDate

class TransactionSaveAndUpdateDto (
    val companyNumber: String,
    val date: LocalDate,
    val price: Int,
    val type: TransactionType
)

fun TransactionSaveAndUpdateDto.toEntity(company: Company) = Transaction(
    id = 0,
    company = company,
    date = date,
    price = price,
    type = type
)