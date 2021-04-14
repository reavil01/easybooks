package com.easybooks.demo

import com.easybooks.demo.domain.Company
import com.easybooks.demo.domain.CompanyRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
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
        assertThat(company.number, `is`(com2.number))
        assertThat(company.name, `is`(com2.name))
        assertThat(company.owner, `is`(com2.owner))
        assertThat(company.address, `is`(com2.address))
        assertThat(company.type, `is`(com2.type))
        assertThat(company.items, `is`(com2.items))
        assertThat(company.email, `is`(com2.email))
        assertThat(company.phone, `is`(com2.phone))
        assertThat(company.fax, `is`(com2.fax))
    }
}