package com.easybooks.demo.web.dto

import com.easybooks.demo.domain.Transaction

class TransactionListResponseDto (transaction: Transaction) {
    val id = transaction.id
    val company = transaction.company
    val date = transaction.date
    val price = transaction.price
    val type = transaction.type
}