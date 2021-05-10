package com.easybooks.demo.web

import com.easybooks.demo.domain.CompanyRepository
import com.easybooks.demo.domain.LedgerRepository
import com.easybooks.demo.domain.TransactionRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class LedgerSearchControllerTest {
    @LocalServerPort
    lateinit var port: java.lang.Integer
    val restTemplate = TestRestTemplate()

    @Autowired
    lateinit var companyRepository: CompanyRepository
    @Autowired
    lateinit var transactionRepository: TransactionRepository
    @Autowired
    lateinit var ledgerRepository: LedgerRepository

    @BeforeEach
    fun tearDown() {
        companyRepository.deleteAll()
        ledgerRepository.deleteAll()
        transactionRepository.deleteAll()
    }

    @Test
    fun `ledger 이름으로 검색 성공`() {
        // given
        val savedCompany = companyRepository.save(getTestCompany())
        val savedLedger = ledgerRepository.save(getTestLedger(savedCompany))
        val keyword = "이퍼"

        // when
        val url = "http://localhost:$port/ledger/search&companyName=$keyword"
        val responseEntity = restTemplate.getForEntity<String>(url, String)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).contains(savedLedger.item)
        assertThat(responseEntity.body).contains(savedLedger.price.toString())
        assertThat(responseEntity.body).contains(savedLedger.total.toString())
    }

}