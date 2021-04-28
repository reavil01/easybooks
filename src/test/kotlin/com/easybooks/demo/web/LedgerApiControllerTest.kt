package com.easybooks.demo.web

import com.easybooks.demo.Ledger
import com.easybooks.demo.LedgerType
import com.easybooks.demo.domain.LedgerRepository
import com.easybooks.demo.web.dto.LedgerSaveRequestDto
import com.easybooks.demo.web.dto.LedgerUpdateRequestDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
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

    @AfterEach
    fun tearDown() {
        ledgerRepository.deleteAll()
    }

    @Test
    fun ledger_등록된다() {
        // given
        val requestDto = LedgerSaveRequestDto(
            companyNumber = "1",
            type = LedgerType.Sell,
            date = LocalDate.now(),
            item = "종이",
            unitPrice = 10,
            quantity = 20,
            price = 30,
            VAT = 40,
            total = 50
        )
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
                VAT = 40,
                total = 50
            )
        )

        val updateId = savedLedger.id
        val expectedCompanyId = "2"
        val expectedDate = LocalDate.now()

        val requestDto = LedgerUpdateRequestDto(
            companyNumber = expectedCompanyId,
            type = LedgerType.Sell,
            date = expectedDate,
            item = "종이",
            unitPrice = 10,
            quantity = 20,
            price = 30,
            VAT = 40,
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
                VAT = 40,
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