package com.easybooks.demo.web

import com.easybooks.demo.Ledger
import com.easybooks.demo.LedgerType
import com.easybooks.demo.domain.Company
import com.easybooks.demo.domain.CompanyRepository
import com.easybooks.demo.domain.LedgerRepository
import com.easybooks.demo.web.dto.LedgerSaveAndUpdateRequestDto
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.time.LocalDate

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class LedgerApiControllerTest {
    @LocalServerPort
    lateinit var port: java.lang.Integer

//    @Autowired
    val restTemplate = TestRestTemplate()

    @Autowired
    lateinit var ledgerRepository: LedgerRepository

    @Autowired
    lateinit var companyRepository: CompanyRepository

    @AfterEach
    fun tearDown() {
        ledgerRepository.deleteAll()
        companyRepository.deleteAll()
    }

    fun _getTestLedgerSaveRequestDto(): LedgerSaveAndUpdateRequestDto {
        return LedgerSaveAndUpdateRequestDto(
            companyNumber = "123",
            type = LedgerType.Sell,
            date = LocalDate.now(),
            item = "종이",
            unitPrice = 10,
            quantity = 20,
            price = 200,
            vat = 20,
            total = 220
        )
    }

    fun _setTestCompany(): Company {
        val company = _getTestCompany()
        return companyRepository.save(company)
    }

    fun _getTestCompany(): Company {
        return Company(
            number = "123",
            name = "종이회사",
            owner = "투명인간",
            address = "지구어딘가",
            type = "가짜회사",
            items = "페이퍼",
            email = "fake@gmail.com",
            phone = "00000000000",
            fax = "11111111111"
        )
    }

    @Test
    fun `ledger 없는 사업자번호면 등록 실패`() {
        // given
        val requestDto = _getTestLedgerSaveRequestDto()
        val url = "http://localhost:$port/api/v1/ledger"

        // when
        val exception: Exception = assertThrows { restTemplate.postForEntity<Long>(url, requestDto, Long) }

//        val exception = assertThatExceptionOfType(IllegalArgumentException::class.java)
//            .isThrownBy {
//                restTemplate.postForEntity<Long>(url, requestDto, Long)
//            }
//        val responseEntity = restTemplate.postForEntity<Long>(url, requestDto, Long)

        // then
        print("EXEXEX:" + exception)
        assertThat(exception.toString()).contains("등록되지 않은 사업자번호입니다.")
    }

    @Test
    fun `ledger 등록된 사업자번호면 등록`() {
        // given
        val savedCompany = _setTestCompany()
        val requestDto = _getTestLedgerSaveRequestDto()
        assertThat(savedCompany.number).isEqualTo(requestDto.companyNumber)

        val url = "http://localhost:$port/api/v1/ledger"

        // when
        val responseEntity = restTemplate.postForEntity<Long>(url, requestDto, Long)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body.toString().toLong()).isGreaterThan(0L)

        val savedLedger = ledgerRepository.findAll()[0]
        assertThat(savedLedger.companyNumber).isEqualTo(requestDto.companyNumber)
        assertThat(savedLedger.type).isEqualTo(requestDto.type)
        assertThat(savedLedger.date).isEqualTo(requestDto.date)
    }

    @Test
    fun ledger_수정된다() {
        // given
        val savedLedger = ledgerRepository.save(
            Ledger(
                companyNumber = "1",
                type = LedgerType.Sell,
                date = LocalDate.now(),
                item = "종이",
                unitPrice = 10,
                quantity = 20,
                price = 30,
                vat = 40,
                total = 50
            )
        )

        val updateId = savedLedger.id
        val expectedCompanyId = "2"
        val expectedDate = LocalDate.now()

        val requestDto = LedgerSaveAndUpdateRequestDto(
            companyNumber = expectedCompanyId,
            type = LedgerType.Sell,
            date = expectedDate,
            item = "종이",
            unitPrice = 10,
            quantity = 20,
            price = 30,
            vat = 40,
            total = 50
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
        assertThat(updatedLedger.companyNumber).isEqualTo(expectedCompanyId)
        assertThat(updatedLedger.date).isEqualTo(expectedDate)
    }

    @Test
    fun ledger_삭제된다() {
        // given
        val savedLedger = ledgerRepository.save(
            Ledger(
                companyNumber = "1",
                type = LedgerType.Sell,
                date = LocalDate.now(),
                item = "종이",
                unitPrice = 10,
                quantity = 20,
                price = 30,
                vat = 40,
                total = 50
            )
        )

        val updateId = savedLedger.id
        val url = "http://localhost:$port/api/v1/ledger/$updateId"

        // when
        restTemplate.delete(url)

        // then
        assertThat(ledgerRepository.findAll().size).isEqualTo(0)
    }
}