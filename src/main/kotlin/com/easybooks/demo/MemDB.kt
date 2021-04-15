package com.easybooks.demo

import com.easybooks.demo.domain.Company

object MemDB {
    val ledgerTable = HashMap<Long, Ledger>()
    val clientTable = HashMap<String, Company>()
}
