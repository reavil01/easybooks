package com.easybooks.demo.domain

import com.easybooks.demo.domain.Ledger
import com.easybooks.demo.domain.LedgerType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.event.annotation.AfterTestClass

@SpringBootTest
class LedgerRepositoryTests {

    @Autowired
    private lateinit var ledgerRepository: LedgerRepository

    @AfterTestClass
    fun cleanup() {
        ledgerRepository.deleteAll()
    }

//    @Test
//    fun ledgerDAO_save_load() {
//        // given
//        val ledger = Ledger(
//            id = 0,
//            companyNumber = "1",
//            type = LedgerType.Sell,
//            date = LocalDate.now(),
//            item = "종이",
//            unitPrice = 10,
//            quantity = 20,
//            price = 30,
//            vat = 40,
//            total = 50
//        )
//        ledgerRepository.save(ledger)
//
//        // when
//        val ledgerList = ledgerRepository.findAll()
//
//        // then
//        val savedLedger = ledgerList[0]
//        assertThat(savedLedger.companyNumber).isEqualTo(ledger.companyNumber)
//        assertThat(savedLedger.type).isEqualTo(ledger.type)
//        assertThat(savedLedger.date).isEqualTo(ledger.date)
//        assertThat(savedLedger.item).isEqualTo(ledger.item)
//    }
}