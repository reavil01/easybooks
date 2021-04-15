package com.easybooks.demo

import com.easybooks.demo.domain.Company
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LedgerTests {
//    private val db = MemDB
//    private val clientManager = ClientManager()
//    private val ledgerManager = LedgerManager()
//
//    @BeforeEach
//    fun setUp() {
//        db.clientTable.clear()
//        db.ledgerTable.clear()
//    }

//    @Test
//    fun saveLedger() {
//        // given
//        val client = Company("123-456-7890", "테스트", "나",
//            "노트북 앞", "테스트데이터", "안팔아요", "지메일",
//            "99999999999", "00000000000")
//        clientManager.saveClient(client)
//
//        val ledger = Ledger(1, "123-456-7890", 111, LedgerType.Sell)
//
//        // when
//        val result = ledgerManager.saveLedger(ledger)
//
//        // then
//        assertTrue(result, "")
//        assertEquals(ledger, db.ledgerTable[ledger.id])
//    }
//
//    @Test
//    fun getLedger() {
//        // given
//        val ledger = Ledger(1, "123-456-7890", 111, LedgerType.Sell)
//        ledgerManager.saveLedger(ledger)
//
//        // when
//        val result = ledgerManager.getLedger(ledger.id)
//
//        // then
//        assertNotNull(result)
//        assertEquals(ledger, result)
//    }
}