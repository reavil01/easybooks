package com.easybooks.demo.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.time.LocalDate

interface TransactionRepository {
    fun save(transaction: Transaction): Transaction
    fun delete(transaction: Transaction)
    fun findAll(): List<Transaction>
    fun findAll(page: Pageable): Page<Transaction>
    fun deleteAll()
    fun findById(id: Long): Transaction?

    fun findAllByCompanyNumberContains(companyNumber: String): List<Transaction>
    fun findAllByCompanyNumberContains(companyNumber: String, page: Pageable): Page<Transaction>
    fun findAllByCompanyNameContains(companyName: String): List<Transaction>
    fun findAllByCompanyNameContains(companyName: String, page: Pageable): Page<Transaction>
    fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<Transaction>
    fun findAllByDateBetween(start: LocalDate, end: LocalDate, page: Pageable): Page<Transaction>

    fun getSumofTotalPrcie(id: Long): Int
}