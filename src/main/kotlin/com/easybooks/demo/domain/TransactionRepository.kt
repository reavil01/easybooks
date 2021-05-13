package com.easybooks.demo.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface TransactionRepository: JpaRepository<Transaction, Long> {
    fun findAllByCompanyNumberContains(companyNumber: String): List<Transaction>
    fun findAllByCompanyNameContains(companyName: String): List<Transaction>
    fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<Transaction>
    @Query("SELECT SUM(t.price) FROM Transaction AS t WHERE t.company.id = :id")
    fun getSumofTotalPrcie(id: Long): Int?
}
