package com.easybooks.demo.domain

import com.easybooks.demo.web.getTestCompany
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.event.annotation.AfterTestClass

@SpringBootTest
class CompanyRepositoryTests {

    @Autowired
    private lateinit var companyRepository: CompanyRepository

    @BeforeEach
    fun cleanup() {
        companyRepository.deleteAll()
    }

    @AfterTestClass
    fun tearDown() {
        companyRepository.deleteAll()
    }

    @Test
    fun companyDAO_save_load() {
        // given
        val company = getTestCompany()
        companyRepository.save(company)

        // when
        val compayList = companyRepository.findAll()

        // then
        val com2 = compayList[0]
        assertThat(com2.number).isEqualTo(company.number)
        assertThat(com2.name).isEqualTo(company.name)
        assertThat(com2.owner).isEqualTo(company.owner)
        assertThat(com2.address).isEqualTo(company.address)
        assertThat(com2.type).isEqualTo(company.type)
        assertThat(com2.items).isEqualTo(company.items)
        assertThat(com2.email).isEqualTo(company.email)
        assertThat(com2.phone).isEqualTo(company.phone)
        assertThat(com2.fax).isEqualTo(company.fax)
    }
}