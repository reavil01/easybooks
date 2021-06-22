package com.easybooks.demo.web.dto.transaction

import com.easybooks.demo.domain.Transaction

class TransactionResponseDto(transaction: Transaction) {
    val id = transaction.id
    val company = transaction.company
    val date = transaction.date
    val price = transaction.price
    val type = transaction.type
}
