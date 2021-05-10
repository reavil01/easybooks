package com.easybooks.demo.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LedgerRepository: JpaRepository<Ledger, Long> {
    fun findAllByOrderByIdDesc(): List<Ledger>
    fun findAllByCompanyNumber(companyNumber: String): List<Ledger>
    fun findAllByCompanyNameContains(companyName: String): List<Ledger>

    @Query("SELECT SUM(l.total) FROM Ledger as l WHERE l.company.number = :companyNumber")
    fun getSumofTotalPrcie(companyNumber: String): Int?
}
