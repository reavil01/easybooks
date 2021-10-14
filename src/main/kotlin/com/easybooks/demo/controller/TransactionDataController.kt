package com.easybooks.demo.controller

import com.easybooks.demo.web.dto.transaction.TransactionSaveAndUpdateDto

interface TransactionDataController {

    fun saveTransaction(requestDto: TransactionSaveAndUpdateDto): Long

    fun updateTransaction(id: Long, requestDto: TransactionSaveAndUpdateDto): Long

    fun deleteTransaction(id: Long): Long
}