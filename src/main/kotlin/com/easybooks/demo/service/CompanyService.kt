package com.easybooks.demo.service

import com.easybooks.demo.domain.*
import com.easybooks.demo.web.company.dto.*
import org.springframework.data.domain.PageRequest
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
    val PAGE_BLOCK_SIZE = 10
    val PAGE_POST_COUNT = 10

    @Transactional
    fun save(requestDto: CompanySaveRequestDto): Long {
        return companyRepository.save(requestDto.toEntity()).id
    }

    @Transactional
    fun update(id: Long, requestDto: CompanyUpdateRequestDto): Long {
        val company = companyRepository.findById(id)
            ?: throw IllegalArgumentException("해당 사업체가 없습니다. id=$id")

        company.update(requestDto)

        return id
    }

    @Transactional
    fun delete(id: Long) {
        val company = companyRepository.findById(id)
            ?: throw IllegalArgumentException("해당 사업체가 없습니다. id=$id")

        companyRepository.delete(company)
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): CompanyResponseDto {
        val entity = companyRepository.findById(id)
            ?: throw IllegalArgumentException("해당 사업체가 없습니다. id=$id")

        return CompanyResponseDto(entity)
    }

    @Transactional(readOnly = true)
    fun findAllDesc(): List<CompanyListResponseDto> {
        return companyRepository.findAllByOrderByIdDesc().stream()
            .map { CompanyListResponseDto(it) }
            .collect(Collectors.toList())
    }

    @Transactional(readOnly = true)
    fun findByNumberContains(number: String): List<CompanyResponseDto> {
        return companyRepository.findAllByNumberContains(number).stream()
            .map { CompanyResponseDto(it) }
            .collect(Collectors.toList())
    }

    @Transactional(readOnly = true)
    fun findByNameContains(name: String): List<CompanyResponseDto> {
        return companyRepository.findAllByNameContains(name).stream()
            .map { CompanyResponseDto(it) }
            .collect(Collectors.toList())
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

    @Transactional(readOnly = true)
    fun findByNameContainsAndUnpaidPrice(name: String): List<CompanyWithUnpaidResponseDto> {
        return companyRepository.findAllByNameContains(name).stream()
            .map {
                // FIX: 매번 DB에서 값을 가져오는 방식은 비효율적
                val total = ledgerRepository.getSumofTotalPrcie(it.id) ?: 0
                val paid = transactionRepository.getSumofTotalPrcie(it.id) ?: 0
                val unpaid = total - paid
                CompanyWithUnpaidResponseDto(it, unpaid)
            }
            .collect(Collectors.toList())
    }

    @Transactional(readOnly = true)
    fun getCompanyListWithUnpaid(pageNum: Int): List<CompanyWithUnpaidResponseDto> {
        val page = companyRepository.findAll(
            PageRequest
                .of(
                    pageNum - 1,
                    PAGE_POST_COUNT,
                    Sort.by(Sort.Direction.ASC, "id")
                )
        )

        return page.map {
            // FIX: 매번 DB에서 값을 가져오는 방식은 비효율적
            val total = ledgerRepository.getSumofTotalPrcie(it.id) ?: 0
            val paid = transactionRepository.getSumofTotalPrcie(it.id) ?: 0
            val unpaid = total - paid
            CompanyWithUnpaidResponseDto(it, unpaid)
        }.toList()
    }

    fun getPrevNextNum(currentPageNum: Int): Pair<Int, Int> {
        var quotient = currentPageNum / PAGE_BLOCK_SIZE
        var remainder = currentPageNum % PAGE_BLOCK_SIZE

        if (remainder > 0) {
            quotient++
        }

        var prev = when ((quotient - 1) * PAGE_BLOCK_SIZE < 1) {
            true -> 1
            else -> (quotient - 1) * PAGE_BLOCK_SIZE
        }

        val lastPage = getLastPageNum()
        val next = when (quotient * PAGE_BLOCK_SIZE + 1 > lastPage) {
            true -> lastPage
            else -> quotient * PAGE_BLOCK_SIZE + 1
        }

        return prev to next
    }

    fun getPageNums(currentPageNum: Int): List<Int> {
        var quotient = currentPageNum / PAGE_BLOCK_SIZE
        var remainder = currentPageNum % PAGE_BLOCK_SIZE

        if (remainder == 0) {
            quotient--
        }

        val start = quotient * PAGE_BLOCK_SIZE + 1
        val end = (quotient + 1) * PAGE_BLOCK_SIZE
        val lastPage = getLastPageNum()

        return when (end > lastPage) {
            true -> (start..lastPage).toList()
            else -> (start..end).toList()
        }
    }

    fun getLastPageNum(): Int {
        return when (companyRepository.count() % PAGE_POST_COUNT > 0) {
            true -> (companyRepository.count() / PAGE_POST_COUNT) + 1
            else -> companyRepository.count() / PAGE_POST_COUNT
        }
    }
}
