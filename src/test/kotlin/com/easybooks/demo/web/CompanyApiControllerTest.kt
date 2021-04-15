package com.easybooks.demo.web

import com.easybooks.demo.domain.Company
import com.easybooks.demo.domain.CompanyRepository
import com.easybooks.demo.web.dto.CompanySaveRequestDto
import com.easybooks.demo.web.dto.CompanyUpdateRequestDto
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

    @AfterEach
    fun tearDown() {
        companyRepository.deleteAll()
    }

    @Test
    fun Compnay_등록된다() {
        // given
        val requestDto = CompanySaveRequestDto(
            number = "123-456-7890",
            name = "페이퍼회사",
            owner = "나",
            address = "어딘가",
            type = "종이",
            items = "종이",
            email = "abc@gmail.com",
            phone = "00000000000",
            fax = "11111111111"
        )
        val url = "http://localhost:$port/api/v1/company"

        // when
        val responseEntity = restTemplate.postForEntity(url, requestDto, Long::class.java)

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
            Company(
                number = "123-456-7890",
                name = "페이퍼컴퍼니",
                owner = "나",
                address = "없어요",
                type = "페이퍼",
                items = "종이",
                email = "paper@gmail.com",
                phone = "00000000000",
                fax = "11111111111"
            )
        )

        val updateId = savedCompany.id
        val expectedNumber = "999-999-9999"
        val expectedName = "골판지회사"

        val requestDto = CompanyUpdateRequestDto(
            number = expectedNumber,
            name = expectedName,
            owner = "나",
            address = "없어요",
            type = "페이퍼",
            items = "종이",
            email = "paper@gmail.com",
            phone = "00000000000",
            fax = "11111111111"
        )

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
        assertThat(updatedCompany.id).isEqualTo(1)
        assertThat(updatedCompany.number).isEqualTo(expectedNumber)
        assertThat(updatedCompany.name).isEqualTo(expectedName)
    }
}