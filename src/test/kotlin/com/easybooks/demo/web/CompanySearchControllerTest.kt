package com.easybooks.demo.web

import com.easybooks.demo.Ledger
import com.easybooks.demo.LedgerType
import com.easybooks.demo.domain.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import java.time.LocalDate

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

    fun getTestCompany(): Company {
        return Company(
            number = "1234567890",
            name = "페이퍼회사",
            owner = "나",
            address = "어딘가",
            type = "종이",
            items = "종이",
            email = "abc@gmail.com",
            phone = "00000000000",
            fax = "11111111111"
        )
    }

    fun getTestTransaction(companyNumber: String, paid: Int) = Transaction(
        id = 0,
        companyNumber = companyNumber,
        date = LocalDate.now(),
        price = paid,
        type = TransactionType.Deposit
    )

    @AfterEach
    fun tearDown() {
        companyRepository.deleteAll()
    }

    @Test
    fun `company 이름으로 검색 성공`() {
        // given
        val company = getTestCompany()
        val savedCompany = companyRepository.save(company)
        val keyword = "이퍼"

        // when
        val url = "http://localhost:$port/company/search&name="+keyword
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
        val url = "http://localhost:$port/company/search&number="+keyword
        val responseEntity = restTemplate.getForEntity<String>(url, String)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).contains(savedCompany.name)
        assertThat(responseEntity.body).contains(savedCompany.number)
    }

    @Test
    fun `company 사업자번호 검색 성공하고 미수금 출력`() {
        // given
        val company = getTestCompany()
        val savedCompany = companyRepository.save(company)

        val ledger = Ledger(
            id = 0,
            companyNumber = company.number,
            type = LedgerType.Sell,
            date = LocalDate.now(),
            item = "종이",
            unitPrice = 50,
            quantity = 100,
            price = 5000,
            vat = 500,
            total = 5500
        )
        ledgerRepository.save(ledger)

        val paid = 100
        val transaction = getTestTransaction(company.number, paid)
        transactionRepository.save(transaction)
        val unpaid = ledger.total - paid

        val keyword = "678"

        // when
        val url = "http://localhost:$port/company/search&unpaid&number="+keyword
        val responseEntity = restTemplate.getForEntity<String>(url, String)

        // then
        println(responseEntity.body)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).contains(savedCompany.name)
        assertThat(responseEntity.body).contains(savedCompany.number)
        assertThat(responseEntity.body).contains(unpaid.toString())
    }
}