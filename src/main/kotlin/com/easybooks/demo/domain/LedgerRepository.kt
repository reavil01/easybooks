package com.easybooks.demo.domain

import java.time.LocalDate

interface LedgerRepository {
    fun save(ledger: Ledger): Ledger
    fun delete(ledger: Ledger)
    fun findAll(): List<Ledger>
    fun deleteAll()
    fun findById(id: Long): Ledger?

    fun findAllByOrderByIdDesc(): List<Ledger>
    fun findAllByCompanyNameContains(companyName: String): List<Ledger>
    fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<Ledger>
    fun findAllByCompanyNumberContains(number: String): List<Ledger>

    fun getSumofTotalPrcie(id: Long): Int
}