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
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.event.annotation.AfterTestClass

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class CompanyApiControllerTest {
    @LocalServerPort
    lateinit var port: java.lang.Integer

    // @Autowired가 왜 안되지?
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
    fun Compnay_등록된다() {
        // given
        val requestDto = getTestCompanySaveRequestDto()
        val url = "http://localhost:$port/api/v1/company"

        // when
        val responseEntity = restTemplate.postForEntity<Long>(url, requestDto, Long)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body.toString().toLong()).isGreaterThan(0L)

        val all = companyRepository.findAll()
        assertThat(all[0].number).isEqualTo(requestDto.number)
        assertThat(all[0].name).isEqualTo(requestDto.name)
        assertThat(all[0].owner).isEqualTo(requestDto.owner)
    }

    // Entity 객체의 값을 val로 선언 하면, update를 위해서는 새 Entity 객체를 생성해야 함
    // 이 경우 더티체킹을 이용할 수 없음 ㅠㅠ
    @Test
    fun company_수정된다() {
        // given
        val savedCompany = companyRepository.save(
            getTestCompany()
        )

        val updateId = savedCompany.id
        val expectedNumber = "999-999-9999"
        val expectedName = "골판지회사"

        val requestDto = getTestCompanyUpdateRequestDto(expectedNumber, expectedName)

        val url = "http://localhost:$port/api/v1/company/$updateId"
        val requestEntity = HttpEntity(requestDto)

        // when
        val responseEntity = restTemplate.exchange<Long>(
            url,
            HttpMethod.POST,
            requestEntity,
            Long
        )

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body.toString().toLong()).isGreaterThan(0L)

        val all = companyRepository.findAll()
        assertThat(all.size).isEqualTo(1)

        val updatedCompany = all[0]
        assertThat(updatedCompany.id).isEqualTo(updateId)
        assertThat(updatedCompany.number).isEqualTo(expectedNumber)
        assertThat(updatedCompany.name).isEqualTo(expectedName)
    }

    @Test
    fun company_삭제된다() {
        // given
        val savedCompany = companyRepository.save(
            getTestCompany()
        )

        val updateId = savedCompany.id
        val url = "http://localhost:$port/api/v1/company/$updateId"

        // when
        restTemplate.delete(url)

        // then
        assertThat(companyRepository.findAll().size).isEqualTo(0)
    }
}