package com.easybooks.demo.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.event.annotation.AfterTestClass

@SpringBootTest
class CompayRepositoryTests {

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
        assertThat(company.number).isEqualTo(com2.number)
        assertThat(company.name).isEqualTo(com2.name)
        assertThat(company.owner).isEqualTo(com2.owner)
        assertThat(company.address).isEqualTo(com2.address)
        assertThat(company.type).isEqualTo(com2.type)
        assertThat(company.items).isEqualTo(com2.items)
        assertThat(company.email).isEqualTo(com2.email)
        assertThat(company.phone).isEqualTo(com2.phone)
        assertThat(company.fax).isEqualTo(com2.fax)
    }
}