package com.easybooks.demo.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TransactionRepository: JpaRepository<Transaction, Long> {
    @Query("SELECT SUM(t.price) FROM Transaction AS t WHERE t.company.id = :id")
    fun getSumofTotalPrcie(id: Long): Int?
}
