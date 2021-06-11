package com.easybooks.demo.domain.jpa.repository

import com.easybooks.demo.domain.Ledger
import com.easybooks.demo.domain.LedgerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class LedgerRepositoryImpl(
    val ledgerRepoJPA: LedgerRepositoryViaJPA
) : LedgerRepository {
    override fun findById(id: Long): Ledger? {
        return ledgerRepoJPA.findByIdOrNull(id)
    }

    override fun findAll(): List<Ledger> {
        return ledgerRepoJPA.findAll()
    }

    override fun delete(ledger: Ledger) {
        return ledgerRepoJPA.delete(ledger)
    }

    override fun deleteAll() {
        return ledgerRepoJPA.deleteAll()
    }

    override fun save(ledger: Ledger): Ledger {
        return ledgerRepoJPA.save(ledger)
    }

    override fun findAllByOrderByIdDesc(): List<Ledger> {
        return ledgerRepoJPA.findAllByOrderByIdDesc()
    }

    override fun findAllByCompanyNameContains(companyName: String): List<Ledger> {
        return ledgerRepoJPA.findAllByCompanyNameContains(companyName)
    }

    override fun findAllByCompanyNameContains(companyName: String, page: Pageable): Page<Ledger> {
        return ledgerRepoJPA.findAllByCompanyNameContains(companyName, page)
    }

    override fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<Ledger> {
        return ledgerRepoJPA.findAllByDateBetween(start, end)
    }

    override fun findAllByDateBetween(start: LocalDate, end: LocalDate, page: Pageable): Page<Ledger> {
        return ledgerRepoJPA.findAllByDateBetween(start, end, page)
    }

    override fun findAllByCompanyNumberContains(number: String): List<Ledger> {
        return ledgerRepoJPA.findAllByCompanyNumberContains(number)
    }

    override fun findAllByCompanyNumberContains(number: String, page: Pageable): Page<Ledger> {
        return ledgerRepoJPA.findAllByCompanyNumberContains(number, page)
    }

    override fun getSumofTotalPrcie(id: Long): Int {
        return ledgerRepoJPA.getSumofTotalPrcie(id) ?: 0
    }
}
