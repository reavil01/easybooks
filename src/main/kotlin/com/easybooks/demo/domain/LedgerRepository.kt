package com.easybooks.demo.domain

import com.easybooks.demo.Ledger
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LedgerRepository: JpaRepository<Ledger, Long> {
    @Query("SELECT l FROM Ledger l ORDER BY l.id DESC")
    fun findAllDesc(): List<Ledger>
}
