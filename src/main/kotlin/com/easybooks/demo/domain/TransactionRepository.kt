package com.easybooks.demo.domain

import java.time.LocalDate

interface TransactionRepository {
    fun save(transaction: Transaction): Transaction
    fun delete(transaction: Transaction)
    fun findAll(): List<Transaction>
    fun deleteAll()
    fun findById(id: Long): Transaction?

    fun findAllByCompanyNumberContains(companyNumber: String): List<Transaction>
    fun findAllByCompanyNameContains(companyName: String): List<Transaction>
    fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<Transaction>

    fun getSumofTotalPrcie(id: Long): Int
}