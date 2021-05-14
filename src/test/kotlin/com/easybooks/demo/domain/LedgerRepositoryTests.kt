package com.easybooks.demo.domain

import com.easybooks.demo.domain.jpa.repository.LedgerRepositoryImpl
import com.easybooks.demo.web.getTestCompany
import com.easybooks.demo.web.getTestLedger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.event.annotation.AfterTestClass

@SpringBootTest
class LedgerRepositoryTests {

    @Autowired
    private lateinit var ledgerRepositoryImpl: LedgerRepositoryImpl

    @Autowired
    private lateinit var companyRepository: CompanyRepository

    @BeforeEach
    fun cleanup() {
        ledgerRepositoryImpl.deleteAll()
        companyRepository.deleteAll()
    }

    @AfterTestClass
    fun tearDown() {
        ledgerRepositoryImpl.deleteAll()
        companyRepository.deleteAll()
    }

    @Test
    fun ledgerDAO_save_load() {
        // given
        val savedCompany = companyRepository.save(getTestCompany())
        val ledger = getTestLedger(savedCompany)
        ledgerRepositoryImpl.save(ledger)

        // when
        val ledgerList = ledgerRepositoryImpl.findAll()

        // then
        val savedLedger = ledgerList[0]
        assertThat(savedLedger.company.id).isEqualTo(ledger.company.id)
        assertThat(savedLedger.type).isEqualTo(ledger.type)
        assertThat(savedLedger.date).isEqualTo(ledger.date)
        assertThat(savedLedger.item).isEqualTo(ledger.item)
    }
}