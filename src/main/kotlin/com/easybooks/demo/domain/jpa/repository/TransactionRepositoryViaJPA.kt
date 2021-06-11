package com.easybooks.demo.domain.jpa.repository

import com.easybooks.demo.domain.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface TransactionRepositoryViaJPA : JpaRepository<Transaction, Long> {
    fun findAllByCompanyNumberContains(companyNumber: String): List<Transaction>
    fun findAllByCompanyNumberContains(companyNumber: String, page: Pageable): Page<Transaction>
    fun findAllByCompanyNameContains(companyName: String): List<Transaction>
    fun findAllByCompanyNameContains(companyName: String, page: Pageable): Page<Transaction>
    fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<Transaction>
    fun findAllByDateBetween(start: LocalDate, end: LocalDate, page: Pageable): Page<Transaction>

    @Query("SELECT SUM(t.price) FROM Transaction AS t WHERE t.company.id = :id")
    fun getSumofTotalPrcie(id: Long): Int?
}
