package com.easybooks.demo.domain

import com.easybooks.demo.domain.Ledger
import com.easybooks.demo.domain.LedgerType
import com.easybooks.demo.web.getTestCompany
import com.easybooks.demo.web.getTestLedger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.event.annotation.AfterTestClass

@SpringBootTest
class LedgerRepositoryTests {

    @Autowired
    private lateinit var ledgerRepository: LedgerRepository

    @Autowired
    private lateinit var companyRepository: CompanyRepository

    @AfterTestClass
    fun cleanup() {
        ledgerRepository.deleteAll()
    }

    @Test
    fun ledgerDAO_save_load() {
        // given
        val savedCompany = companyRepository.save(getTestCompany())
        val ledger = getTestLedger(savedCompany)
        ledgerRepository.save(ledger)

        // when
        val ledgerList = ledgerRepository.findAll()

        // then
        val savedLedger = ledgerList[0]
        assertThat(savedLedger.company.id).isEqualTo(ledger.company.id)
        assertThat(savedLedger.type).isEqualTo(ledger.type)
        assertThat(savedLedger.date).isEqualTo(ledger.date)
        assertThat(savedLedger.item).isEqualTo(ledger.item)
    }
}