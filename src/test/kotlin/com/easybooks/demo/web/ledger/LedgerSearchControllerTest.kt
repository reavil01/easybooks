package com.easybooks.demo.web.ledger

import com.easybooks.demo.domain.CompanyRepository
import com.easybooks.demo.domain.LedgerRepository
import com.easybooks.demo.domain.TransactionRepository
import com.easybooks.demo.web.getTestCompany
import com.easybooks.demo.web.getTestLedger
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.event.annotation.AfterTestClass
import java.time.LocalDate

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
    fun setup() {
        ledgerRepository.deleteAll()
        transactionRepository.deleteAll()
        companyRepository.deleteAll()
    }

    @AfterTestClass
    fun tearDown() {
        ledgerRepository.deleteAll()
        transactionRepository.deleteAll()
        companyRepository.deleteAll()
    }

    @Test
    fun `ledger 이름으로 검색 성공`() {
        // given
        val savedCompany = companyRepository.save(getTestCompany())
        val savedLedger = ledgerRepository.save(getTestLedger(savedCompany))
        val keyword = "이퍼"

        // when
        val url = "http://localhost:$port/ledger/search/companyName=$keyword"
        val responseEntity = restTemplate.getForEntity<String>(url, String)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).contains(savedLedger.item)
        assertThat(responseEntity.body).contains(savedLedger.price.toString())
        assertThat(responseEntity.body).contains(savedLedger.total.toString())
    }

    @Test
    fun `ledger 날짜로 검색 성공`() {
        // given
        val startDate = LocalDate.now().minusDays(3)
        val endDate = LocalDate.now()

        val savedCompany = companyRepository.save(getTestCompany())
        val pastLedger = getTestLedger(savedCompany)
        pastLedger.date = startDate
        pastLedger.item = "아무것도안샀는데!"
        ledgerRepository.save(pastLedger)
        val savedLedger = ledgerRepository.save(getTestLedger(savedCompany))

        // when
        val url = "http://localhost:$port/ledger/search/startDate=$startDate&endDate=$endDate"
        val responseEntity = restTemplate.getForEntity<String>(url, String)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).contains(pastLedger.item)
        assertThat(responseEntity.body).contains(savedLedger.item)
        assertThat(responseEntity.body).contains(savedLedger.price.toString())
        assertThat(responseEntity.body).contains(savedLedger.total.toString())
    }

}