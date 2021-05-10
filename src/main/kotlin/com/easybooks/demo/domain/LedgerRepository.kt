package com.easybooks.demo.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface LedgerRepository: JpaRepository<Ledger, Long> {
    fun findAllByOrderByIdDesc(): List<Ledger>
    fun findAllByCompanyNameContains(companyName: String): List<Ledger>
    fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<Ledger>

    @Query("SELECT SUM(l.total) FROM Ledger as l WHERE l.company.id = :id")
    fun getSumofTotalPrcie(id: Long): Int?
}
