package com.easybooks.demo.domain

import com.easybooks.demo.Ledger
import org.springframework.data.jpa.repository.JpaRepository

interface LedgerRepository: JpaRepository<Ledger, Long> {

}
