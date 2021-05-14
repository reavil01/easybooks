package com.easybooks.demo.domain.jpa.repository

import com.easybooks.demo.domain.Ledger
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface LedgerRepositoryViaJPA: JpaRepository<Ledger, Long> {
    fun findAllByOrderByIdDesc(): List<Ledger>
    fun findAllByCompanyNameContains(companyName: String): List<Ledger>
    fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<Ledger>
    fun findAllByCompanyNumberContains(number: String): List<Ledger>

    @Query("SELECT SUM(l.total) FROM Ledger as l WHERE l.company.id = :id")
    fun getSumofTotalPrcie(id: Long): Int
}