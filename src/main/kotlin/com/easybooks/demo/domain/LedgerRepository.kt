package com.easybooks.demo.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate

interface LedgerRepository {
    fun save(ledger: Ledger): Ledger
    fun delete(ledger: Ledger)
    fun findAll(): List<Ledger>
    fun findAll(page: Pageable): Page<Ledger>
    fun deleteAll()
    fun findById(id: Long): Ledger?

    fun findAllByOrderByIdDesc(): List<Ledger>
    fun findAllByCompanyNameContains(companyName: String): List<Ledger>
    fun findAllByCompanyNameContains(companyName: String, page: Pageable): Page<Ledger>
    fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<Ledger>
    fun findAllByDateBetween(start: LocalDate, end: LocalDate, page: Pageable): Page<Ledger>
    fun findAllByCompanyNumberContains(number: String): List<Ledger>
    fun findAllByCompanyNumberContains(number: String, page: Pageable): Page<Ledger>

    fun getSumofTotalPrcie(id: Long): Int
}