package com.easybooks.demo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class BusinessManagerTests {
    private val businessManager = BusinessManager()
    private val clientManager = ClientManager()
    private val ledgerManager = LedgerManager()

    @Test
    fun createLedgerTest() {
        // given
        val clientId = "123-456-7890"
        val client = Client(clientId, "테스트")
        clientManager.saveClient(client)

        // when
        businessManager.registerLedger(clientId, 3000, LedgerType.Sell)

        // then
        val ledger = ledgerManager.getLedger(1)
        assertEquals(ledger?.total, 3000)
    }

//    @Test
//    fun createLedgerWhenClientIdNotExist() {
//        // given
//        val clientId = "123-456-7890"
//        val client = Client(clientId, "테스트")
//        clientManager.saveClient(client)
//
//        // when
//        // FIX: 임시로 classNotFoundException 발생
//        val exception = assertThrows(ClassNotFoundException::class.java) {
//            businessManager.registerLedger("111-111-1111", 3000, LedgerType.Sell)
//        }
//
//        // then
//        // FIX: Exception 클래스가 무엇을 뱉는지 몰라 임시로 설정
//        print(exception.message)
////        assertEquals(ledger.total, 3000)
//    }
}