package com.easybooks.demo.web

import com.easybooks.demo.domain.Company
import com.easybooks.demo.domain.CompanyRepository
import com.easybooks.demo.domain.LedgerRepository
import com.easybooks.demo.domain.TransactionRepository
import com.easybooks.demo.web.dto.company.CompanySaveRequestDto
import com.easybooks.demo.web.dto.company.toEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort


@SpringBootTest
class PageNavigationServiceTest() {
    @Autowired
    lateinit var companyRepository: CompanyRepository

    @Autowired
    lateinit var ledgerRepository: LedgerRepository

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @BeforeEach
    fun setup() {
        ledgerRepository.deleteAll()
        transactionRepository.deleteAll()
        companyRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        ledgerRepository.deleteAll()
        transactionRepository.deleteAll()
        companyRepository.deleteAll()
    }

    @Test
    fun `사업체 명을 검색했을 때, 페이징 성공`() {
        // number, name, owner, address, type, items, email, phone, fax
        val targetCompnays = ArrayList<Company>()
        for (number in 1..20) {
            val dummy = number.toString()
            val dummyCompany = CompanySaveRequestDto(dummy, "페이퍼", dummy, dummy, dummy, dummy, dummy).toEntity()
            targetCompnays.add(dummyCompany)
            companyRepository.save(dummyCompany)
        }
        for (number in 21..30) {
            val dummy = number.toString()
            companyRepository.save(CompanySaveRequestDto(dummy, dummy, dummy, dummy, dummy, dummy, dummy).toEntity())
        }

        val keyword = "이퍼"
        val pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "id")
        val page = companyRepository.findAllByNameContains(keyword, pageable)

        assertThat(page.totalElements).isEqualTo(20)
        assertThat(page.totalPages).isEqualTo(2)

        for(index in page.content.indices){
            val company = page.content[index]
            assertThat(company.id).isEqualTo(targetCompnays[index].id)
            assertThat(company.number).isEqualTo(targetCompnays[index].number)
            assertThat(company.name).isEqualTo(targetCompnays[index].name)
            assertThat(company.owner).isEqualTo(targetCompnays[index].owner)
            assertThat(company.address).isEqualTo(targetCompnays[index].address)
            assertThat(company.type).isEqualTo(targetCompnays[index].type)
            assertThat(company.items).isEqualTo(targetCompnays[index].items)
            assertThat(company.email).isEqualTo(targetCompnays[index].email)
        }
    }
}