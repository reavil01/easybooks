package com.easybooks.demo

class LedgerManager {
    private val db = MemDB

    fun saveLedger(ledger: Ledger): Boolean {
        if(db.ledgerTable[ledger.id] == null) {
            db.ledgerTable[ledger.id] = ledger
            return true
        }
        return false
    }

    fun getLedger(id: Long): Ledger? {
        return db.ledgerTable[id]
    }
}
