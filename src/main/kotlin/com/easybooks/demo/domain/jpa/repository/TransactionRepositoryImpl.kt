package com.easybooks.demo.domain.jpa.repository

import com.easybooks.demo.domain.Transaction
import com.easybooks.demo.domain.TransactionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class TransactionRepositoryImpl(
    val transactionRepoJPA: TransactionRepositoryViaJPA
) : TransactionRepository {
    override fun findById(id: Long): Transaction? {
        return transactionRepoJPA.findByIdOrNull(id)
    }

    override fun save(transaction: Transaction): Transaction {
        return transactionRepoJPA.save(transaction)
    }

    override fun delete(transaction: Transaction) {
        return transactionRepoJPA.delete(transaction)
    }

    override fun findAll(): List<Transaction> {
        return transactionRepoJPA.findAll()
    }

    override fun deleteAll() {
        return transactionRepoJPA.deleteAll()
    }

    override fun findAllByCompanyNumberContains(companyNumber: String): List<Transaction> {
        return transactionRepoJPA.findAllByCompanyNumberContains(companyNumber)
    }

    override fun findAllByCompanyNumberContains(companyNumber: String, page: Pageable): Page<Transaction> {
        return transactionRepoJPA.findAllByCompanyNumberContains(companyNumber, page)
    }

    override fun findAllByCompanyNameContains(companyName: String): List<Transaction> {
        return transactionRepoJPA.findAllByCompanyNameContains(companyName)
    }

    override fun findAllByCompanyNameContains(companyName: String, page: Pageable): Page<Transaction> {
        return transactionRepoJPA.findAllByCompanyNameContains(companyName, page)
    }

    override fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<Transaction> {
        return transactionRepoJPA.findAllByDateBetween(start, end)
    }

    override fun findAllByDateBetween(start: LocalDate, end: LocalDate, page: Pageable): Page<Transaction> {
        return transactionRepoJPA.findAllByDateBetween(start, end, page)
    }

    override fun getSumofTotalPrcie(id: Long): Int {
        return transactionRepoJPA.getSumofTotalPrcie(id) ?: 0
    }
}