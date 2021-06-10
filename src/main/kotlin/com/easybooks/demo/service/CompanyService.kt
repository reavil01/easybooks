package com.easybooks.demo.service

import com.easybooks.demo.domain.*
import com.easybooks.demo.web.company.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException
import java.util.stream.Collectors

@Service
// 생성자가 하나인 경우 @Autowired 생략해도 injection 됨
class CompanyService(
    val companyRepository: CompanyRepository,
    val ledgerRepository: LedgerRepository,
    val transactionRepository: TransactionRepository
) {

    fun save(requestDto: CompanySaveRequestDto): Long {
        return companyRepository.save(requestDto.toEntity()).id
    }

    fun update(id: Long, requestDto: CompanyUpdateRequestDto): Long {
        val company = companyRepository.findById(id)
            ?: throw IllegalArgumentException("해당 사업체가 없습니다. id=$id")
        company.update(requestDto)

        return id
    }

    fun delete(id: Long) {
        val company = companyRepository.findById(id)
            ?: throw IllegalArgumentException("해당 사업체가 없습니다. id=$id")

        companyRepository.delete(company)
    }

    fun findAll(page: Pageable): Page<CompanyWithUnpaidResponseDto> {
        val companyPage = companyRepository.findAll(page)
        return companyToCompanyWithUnpaidResponseDto(companyPage)
    }

    fun findByNameContains(name: String, page: Pageable): Page<CompanyWithUnpaidResponseDto> {
        val companyPage = companyRepository.findAllByNameContains(name, page)
        return companyToCompanyWithUnpaidResponseDto(companyPage)
    }

    fun companyToCompanyWithUnpaidResponseDto(page: Page<Company>): Page<CompanyWithUnpaidResponseDto> {
        return page.map {
            // FIX: 매번 DB에서 값을 가져오는 방식은 비효율적?
            val total = ledgerRepository.getSumofTotalPrcie(it.id)
            val paid = transactionRepository.getSumofTotalPrcie(it.id)
            val unpaid = total - paid
            CompanyWithUnpaidResponseDto(it, unpaid)
        }
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): CompanyResponseDto {
        val entity = companyRepository.findById(id)
            ?: throw IllegalArgumentException("해당 사업체가 없습니다. id=$id")

        return CompanyResponseDto(entity)
    }


    @Transactional(readOnly = true)
    fun findByNumberContainsAndUnpaidPrice(number: String): List<CompanyWithUnpaidResponseDto> {
        return companyRepository.findAllByNumberContains(number).stream()
            .map {
                val total = ledgerRepository.getSumofTotalPrcie(it.id) ?: 0
                val paid = transactionRepository.getSumofTotalPrcie(it.id) ?: 0
                val unpaid = total - paid
                CompanyWithUnpaidResponseDto(it, unpaid)
            }
            .collect(Collectors.toList())
    }
}
