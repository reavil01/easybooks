package com.easybooks.demo.web

import com.easybooks.demo.domain.CompanyRepository
import com.easybooks.demo.domain.LedgerRepository
import com.easybooks.demo.web.dto.LedgerSaveAndUpdateRequestDto
import com.easybooks.demo.web.dto.toEntity
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.event.annotation.AfterTestClass
import java.time.LocalDate

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class LedgerApiControllerTest {
    @LocalServerPort
    lateinit var port: java.lang.Integer

    val restTemplate = TestRestTemplate()

    @Autowired
    lateinit var ledgerRepository: LedgerRepository

    @Autowired
    lateinit var companyRepository: CompanyRepository

    @BeforeEach
    fun setup() {
        ledgerRepository.deleteAll()
        companyRepository.deleteAll()
    }

    @AfterTestClass
    fun tearDown() {
        ledgerRepository.deleteAll()
        companyRepository.deleteAll()
    }

    @Test
    fun `ledger 없는 사업자번호면 등록 실패`() {
        // given
        val notSavedCompany = getTestCompany()
        val requestDto = getTestLedgerSaveRequestDto(notSavedCompany)
        val url = "http://localhost:$port/api/v1/ledger"

        // when
        val response = restTemplate.postForEntity<String>(url, requestDto, String)

        // then
        assertThat(response.statusCode).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
        assertThat(response.body).contains("등록되지 않은 사업자번호입니다.")
    }

    @Test
    fun `ledger 등록된 사업자번호면 등록`() {
        // given
        val savedCompany = companyRepository.save(getTestCompany())
        val requestDto = getTestLedgerSaveRequestDto(savedCompany)
        assertThat(savedCompany).isEqualTo(requestDto.company)

        val url = "http://localhost:$port/api/v1/ledger"

        // when
        val responseEntity = restTemplate.postForEntity<String>(url, requestDto, String)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body.toString().toLong()).isGreaterThan(0L)

        val savedLedger = ledgerRepository.findAll()[0]
        assertThat(savedLedger.company.id).isEqualTo(requestDto.company.id)
        assertThat(savedLedger.company.number).isEqualTo(requestDto.company.number)
        assertThat(savedLedger.type).isEqualTo(requestDto.type)
        assertThat(savedLedger.date).isEqualTo(requestDto.date)
    }

    @Test
    fun ledger_수정된다() {
        // given
        val savedCompany = companyRepository.save(getTestCompany())
        val originalLedger = getTestLedgerSaveRequestDto(savedCompany)
        val savedLedger = ledgerRepository.save(originalLedger.toEntity())

        val updateId = savedLedger.id
        val expectedItem = "지폐"
        val expectedDate = LocalDate.now()

        val requestDto = LedgerSaveAndUpdateRequestDto(
            company = savedLedger.company,
            type = savedLedger.type,
            date = expectedDate,
            item = expectedItem,
            unitPrice = savedLedger.unitPrice,
            quantity = savedLedger.quantity,
            price = savedLedger.price,
            vat = savedLedger.vat,
            total = savedLedger.total,
        )

        val url = "http://localhost:$port/api/v1/ledger/$updateId"
        val requestEntity = HttpEntity(requestDto)

        // when
        val responseEntity = restTemplate.exchange<Long>(url, HttpMethod.POST, requestEntity, Long)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body.toString().toLong()).isGreaterThan(0L)

        val all = ledgerRepository.findAll()
        assertThat(all.size).isEqualTo(1)

        val updatedLedger = all[0]
        assertThat(updatedLedger.id).isEqualTo(updateId)
        assertThat(updatedLedger.date).isEqualTo(expectedDate)
        assertThat(updatedLedger.item).isEqualTo(expectedItem)
        assertThat(updatedLedger.price).isEqualTo(savedLedger.price)
        assertThat(updatedLedger.vat).isEqualTo(savedLedger.vat)
    }

    @Test
    fun ledger_삭제된다() {
        // given
        val savedCompany = companyRepository.save(getTestCompany())
        val requsetDto = getTestLedgerSaveRequestDto(savedCompany)
        val savedLedger = ledgerRepository.save(requsetDto.toEntity())

        val updateId = savedLedger.id
        val url = "http://localhost:$port/api/v1/ledger/$updateId"

        // when
        restTemplate.delete(url)

        // then
        assertThat(ledgerRepository.findAll().size).isEqualTo(0)
    }
}