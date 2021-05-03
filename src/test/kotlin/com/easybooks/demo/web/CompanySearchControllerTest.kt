package com.easybooks.demo.web

import com.easybooks.demo.domain.Company
import com.easybooks.demo.domain.CompanyRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class CompanySearchControllerTest {
    @LocalServerPort
    lateinit var port: java.lang.Integer
    val restTemplate = TestRestTemplate()

    @Autowired
    lateinit var companyRepository: CompanyRepository

    fun getTestCompany(): Company {
        return Company(
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
    }

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
        assertThat(responseEntity.body).doesNotContain(savedCompany.name)
        assertThat(responseEntity.body).doesNotContain(savedCompany.number)
    }
}