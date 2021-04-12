package com.easybooks.demo

class BusinessManager {
    private val clientManager = ClientManager()
    private val ledgerManager = LedgerManager()

    fun registerLedger(clientId: String, total: Int, type: LedgerType) {
        if(clientManager.getClient(clientId) == null) {
            throw ClassNotFoundException()
        }

        val ledger = createLedger(clientId, total, type)
        ledgerManager.saveLedger(ledger)
    }

    private fun createLedger(
        clientId: String,
        total: Int,
        type: LedgerType
    ) = Ledger(ledgerManager.getNewId(), clientId, total, type)
}