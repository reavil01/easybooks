package com.easybooks.demo.web.transaction

import com.easybooks.demo.domain.CompanyRepository
import com.easybooks.demo.domain.LedgerRepository
import com.easybooks.demo.domain.TransactionRepository
import com.easybooks.demo.web.getTestCompany
import com.easybooks.demo.web.getTestTransaction
import org.assertj.core.api.Assertions
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
class TransactionSeearchControllerTest {
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
    fun `transaction 회사명으로 검색 성공`() {
        // given
        val savedCompany = companyRepository.save(getTestCompany())
        val savedTransaction = transactionRepository.save(getTestTransaction(savedCompany, 100))
        val keyword = "이퍼"

        // when
        val url = "http://localhost:$port/transaction/search/companyName=$keyword"
        val responseEntity = restTemplate.getForEntity<String>(url, String)

        // then
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(responseEntity.body).contains(savedCompany.name)
        Assertions.assertThat(responseEntity.body).contains(savedTransaction.date.toString())
        Assertions.assertThat(responseEntity.body).contains(savedTransaction.price.toString())
    }

    @Test
    fun `transaction 사업자번호로 검색 성공`() {
        // given
        val savedCompany = companyRepository.save(getTestCompany())
        val savedTransaction = transactionRepository.save(getTestTransaction(savedCompany, 100))
        val keyword = "6789"

        // when
        val url = "http://localhost:$port/transaction/search/companyNumber=$keyword"
        val responseEntity = restTemplate.getForEntity<String>(url, String)

        // then
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(responseEntity.body).contains(savedCompany.name)
        Assertions.assertThat(responseEntity.body).contains(savedTransaction.date.toString())
        Assertions.assertThat(responseEntity.body).contains(savedTransaction.price.toString())
    }

    @Test
    fun `transaction 날짜로 검색 성공`() {
        // given
        val savedCompany = companyRepository.save(getTestCompany())
        val savedTransaction = transactionRepository.save(getTestTransaction(savedCompany, 100))
        val start = LocalDate.now().minusDays(3)
        val end = LocalDate.now()

        // when
        val url = "http://localhost:$port/transaction/search/startDate=$start&endDate=$end"
        val responseEntity = restTemplate.getForEntity<String>(url, String)

        // then
        Assertions.assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(responseEntity.body).contains(savedCompany.name)
        Assertions.assertThat(responseEntity.body).contains(savedTransaction.date.toString())
        Assertions.assertThat(responseEntity.body).contains(savedTransaction.price.toString())
    }
}