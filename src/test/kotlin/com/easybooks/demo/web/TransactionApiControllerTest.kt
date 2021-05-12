package com.easybooks.demo.web

import com.easybooks.demo.domain.*
import com.easybooks.demo.web.dto.TransactionSaveAndUpdateDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.event.annotation.AfterTestClass
import java.time.LocalDate

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class TransactionApiControllerTest {
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
    fun `transaction 등록` () {
        // given
        val savedCompany = companyRepository.save(getTestCompany())
        val requestDto = TransactionSaveAndUpdateDto(
            companyNumber = savedCompany.number,
            date = LocalDate.now(),
            price = 100,
            type = TransactionType.Deposit
        )
        val url = "http://localhost:$port/api/v1/transaction"

        // when
        val response = restTemplate.postForEntity<String>(url, requestDto, String)

        // then
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body.toString().toLong()).isGreaterThan(0L)

        val savedTransaction = transactionRepository.findAll()[0]
        assertThat(savedTransaction.company.number).isEqualTo(requestDto.companyNumber)
        assertThat(savedTransaction.date).isEqualTo(requestDto.date)
        assertThat(savedTransaction.price).isEqualTo(requestDto.price)
    }

    @Test
    fun `transaction 업데이트` () {
        // given
        val savedCompany = companyRepository.save(getTestCompany())
        val savedTransaction = transactionRepository.save(getTestTransaction(savedCompany, 100))
        val requestDto = TransactionSaveAndUpdateDto(
            companyNumber = savedCompany.number,
            date = LocalDate.now().minusDays(3),
            price = 200,
            type = TransactionType.Deposit
        )
        val url = "http://localhost:$port/api/v1/transaction/${savedTransaction.id}"

        // when
        val response = restTemplate.postForEntity<String>(url, requestDto, String)

        // then
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body.toString().toLong()).isGreaterThan(0L)

        val updatedTransaction = transactionRepository.findAll()[0]
        assertThat(updatedTransaction.date).isEqualTo(requestDto.date)
        assertThat(updatedTransaction.price).isEqualTo(requestDto.price)
    }

    @Test
    fun `transaction 삭제` () {
        // given
        val savedCompany = companyRepository.save(getTestCompany())
        val savedTransaction = transactionRepository.save(getTestTransaction(savedCompany, 100))
        val url = "http://localhost:$port/api/v1/transaction/${savedTransaction.id}"

        // when
        val response = restTemplate.delete(url)

        // then
        val numOfTransactions = transactionRepository.findAll().size
        assertThat(numOfTransactions).isEqualTo(0)
    }
}