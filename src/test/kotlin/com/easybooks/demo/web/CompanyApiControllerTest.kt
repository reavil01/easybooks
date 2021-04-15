package com.easybooks.demo.web

import com.easybooks.demo.domain.CompanyRepository
import com.easybooks.demo.web.dto.CompanySaveRequestDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
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
        val responseEntity = restTemplate.postForEntity<Long>(url, requestDto)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body.toString().toLong()).isGreaterThan(0L)

        val all = companyRepository.findAll()
        assertThat(all[0].number).isEqualTo(requestDto.number)
        assertThat(all[0].name).isEqualTo(requestDto.name)
        assertThat(all[0].owner).isEqualTo(requestDto.owner)
    }
}