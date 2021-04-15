package com.easybooks.demo.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.event.annotation.AfterTestClass

@SpringBootTest
class CompanyRepositoryTests {

    @Autowired
    private lateinit var companyRepository: CompanyRepository

    @AfterTestClass
    fun cleanup() {
        companyRepository.deleteAll()
    }

    @Test
    fun companyDAO_save_load() {
        // given
        val company = Company(
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