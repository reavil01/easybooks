package com.easybooks.demo

import com.easybooks.demo.domain.Company
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BusinessManagerTests {
//    private val businessManager = BusinessManager()
//    private val clientManager = ClientManager()
//    private val ledgerManager = LedgerManager()

//    @Test
//    fun createLedgerTest() {
//        // given
//        val clientId = "123-456-7890"
//        val client = Company(clientId, "테스트", "나",
//            "노트북 앞", "테스트데이터", "안팔아요", "지메일",
//            "99999999999", "00000000000")
//        clientManager.saveClient(client)
//
//        // when
//        businessManager.registerLedger(clientId, 3000, LedgerType.Sell)
//
//        // then
//        val ledger = ledgerManager.getLedger(1)
//        assertEquals(ledger?.total, 3000)
//    }
//
//    @Test
//    fun createLedgerWhenClientIdNotExist() {
//        // given
//        val clientId = "123-456-7890"
//        val client = Company(clientId, "테스트", "나",
//            "노트북 앞", "테스트데이터", "안팔아요", "지메일",
//            "99999999999", "00000000000")
//        clientManager.saveClient(client)
//
//        // when
//        val exception = assertThrows(ClientNotExistException::class.java) {
//            businessManager.registerLedger("111-111-1111", 3000, LedgerType.Sell)
//        }
//
//        // then
//        assertTrue(exception.toString().contains("111-111-1111"))
//    }
}