package com.easybooks.demo.web.company

import com.easybooks.demo.domain.*
import com.easybooks.demo.web.getTestCompany
import com.easybooks.demo.web.getTestLedger
import com.easybooks.demo.web.getTestTransaction
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

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class CompanySearchControllerTest {
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
    fun `company 이름으로 검색 성공`() {
        // given
        val company = getTestCompany()
        val savedCompany = companyRepository.save(company)
        val keyword = "이퍼"

        // when
        val url = "http://localhost:$port/company/search&name=$keyword"
        val responseEntity = restTemplate.getForEntity<String>(url, String)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).contains(savedCompany.name)
        assertThat(responseEntity.body).contains(savedCompany.number)
    }

    @Test
    fun `company 사업자번호로 검색 성공`() {
        // given
        val company = getTestCompany()
        val savedCompany = companyRepository.save(company)
        val keyword = "6789"

        // when
        val url = "http://localhost:$port/company/search&number=$keyword"
        val responseEntity = restTemplate.getForEntity<String>(url, String)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).contains(savedCompany.name)
        assertThat(responseEntity.body).contains(savedCompany.number)
    }

    @Test
    fun `company 사업자번호 검색 성공하고 미수금이 존재할 때 미수금 출력`() {
        // given
        val company = getTestCompany()
        val savedCompany = companyRepository.save(company)

        val ledger = getTestLedger(savedCompany)
        ledgerRepository.save(ledger)

        val paid = 100
        val transaction = getTestTransaction(company, paid)
        transactionRepository.save(transaction)
        val unpaid = ledger.total - paid

        val keyword = "678"

        // when
        val url = "http://localhost:$port/company/search/unpaid&number=$keyword"
        val responseEntity = restTemplate.getForEntity<String>(url, String)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).contains(savedCompany.name)
        assertThat(responseEntity.body).contains(savedCompany.number)
        assertThat(responseEntity.body).contains(unpaid.toString())
    }

    @Test
    fun `company 사업자번호 검색 성공하고 미수금이 없을 때 미수금 0 출력`() {
        // given
        val company = getTestCompany()
        val savedCompany = companyRepository.save(company)

        val ledger = getTestLedger(savedCompany)
        ledgerRepository.save(ledger)
        val unpaid = ledger.total

        val keyword = "678"

        // when
        val url = "http://localhost:$port/company/search/unpaid&number=$keyword"
        val responseEntity = restTemplate.getForEntity<String>(url, String)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).contains(savedCompany.name)
        assertThat(responseEntity.body).contains(savedCompany.number)
        assertThat(responseEntity.body).contains(unpaid.toString())
    }

    @Test
    fun `company 이름 검색 성공하고 미수금 출력`() {
        // given
        val company = getTestCompany()
        val savedCompany = companyRepository.save(company)

        val ledger = getTestLedger(savedCompany)
        ledgerRepository.save(ledger)

        val paid = 100
        val transaction = getTestTransaction(company, paid)
        transactionRepository.save(transaction)
        val unpaid = ledger.total - paid

        val keyword = "이퍼"

        // when
        val url = "http://localhost:$port/company/search/unpaid&name=$keyword"
        val responseEntity = restTemplate.getForEntity<String>(url, String)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).contains(savedCompany.name)
        assertThat(responseEntity.body).contains(savedCompany.number)
        assertThat(responseEntity.body).contains(unpaid.toString())
    }
}