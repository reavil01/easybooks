package com.easybooks.demo

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LedgerTests {
    private val db = MemDB
    private val clientManager = ClientManager()
    private val ledgerManager = LedgerManager()

    @BeforeEach
    fun setUp() {
        db.clientTable.clear()
        db.ledgerTable.clear()
    }

    @Test
    fun saveLedger() {
        // given
        val client = Client("123-456-7890", "test")
        clientManager.saveClient(client)

        val ledger = Ledger(1, "123-456-7890", 111, LedgerType.Sell)

        // when
        val result = ledgerManager.saveLedger(ledger)

        // then
        assertTrue(result, "")
        assertEquals(ledger, db.ledgerTable[ledger.id])
    }

    @Test
    fun getLedger() {
        // given
        val ledger = Ledger(1, "123-456-7890", 111, LedgerType.Sell)
        ledgerManager.saveLedger(ledger)

        // when
        val result = ledgerManager.getLedger(ledger.id)

        // then
        assertNotNull(result)
        assertEquals(ledger, result)
    }
}