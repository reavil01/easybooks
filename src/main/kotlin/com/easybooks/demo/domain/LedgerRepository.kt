package com.easybooks.demo.domain

import com.easybooks.demo.Ledger
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LedgerRepository: JpaRepository<Ledger, Long> {
    fun findAllByOrderByIdDesc(): List<Ledger>

    @Query("SELECT SUM(l.total) FROM Ledger as l WHERE l.companyNumber = :companyNumber")
    fun getSumofTotalPrcie(companyNumber: String): Int?
}
