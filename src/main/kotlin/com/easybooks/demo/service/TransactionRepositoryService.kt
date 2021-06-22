package com.easybooks.demo.service

import com.easybooks.demo.domain.*
import com.easybooks.demo.web.dto.company.toEntity
import com.easybooks.demo.web.dto.transaction.TransactionListResponseDto
import com.easybooks.demo.web.dto.transaction.TransactionResponseDto
import com.easybooks.demo.web.dto.transaction.TransactionSaveAndUpdateDto
import com.easybooks.demo.web.dto.transaction.toEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.time.LocalDate

@Service
class TransactionRepositoryService(
    val transactionRepository: TransactionRepository,
    val companyRepositoryService: CompanyRepositoryService,
) {
    fun save(requestDto: TransactionSaveAndUpdateDto): Long {
        val company = companyRepositoryService.findByNumber(requestDto.companyNumber).toEntity()
        val transaction = requestDto.toEntity(company)

        return transactionRepository.save(transaction).id
    }

    fun update(id: Long, requestDto: TransactionSaveAndUpdateDto): Long {
        val transaction = transactionRepository.findById(id)
            ?: throw IllegalArgumentException("해당 입/출금 내역이 없습니다 id = $id")

        transaction.update(requestDto)
        transactionRepository.save(transaction)

        return id
    }

    fun delete(id: Long) {
        val transaction = transactionRepository.findById(id)
            ?: throw IllegalArgumentException("해당 입/출금 내역이 없습니다 id = $id")

        transactionRepository.delete(transaction)
    }

    fun findById(id: Long): TransactionResponseDto {
        val entity = transactionRepository.findById(id)
            ?: throw IllegalArgumentException("해당 입/출금 내역이 없습니다. transaction id = $id")

        return TransactionResponseDto(entity)
    }

    fun findAll(page: Pageable): Page<TransactionListResponseDto> {
        val transactionPgae = transactionRepository.findAll(page)
        return transactionPgae.map { TransactionListResponseDto(it) }
    }

    fun findAllByCompanyNameContains(companyName: String, page: Pageable): Page<TransactionListResponseDto> {
        val transactionPage = transactionRepository.findAllByCompanyNameContains(companyName, page)
        return transactionPage.map { TransactionListResponseDto(it) }
    }

    fun findAllByCompanyNumberContains(companyNumber: String, page: Pageable): Page<TransactionListResponseDto> {
        val transactionPage = transactionRepository.findAllByCompanyNumberContains(companyNumber, page)
        return transactionPage.map { TransactionListResponseDto(it) }
    }

    fun findAllByDateBetween(
        startDate: LocalDate,
        endDate: LocalDate,
        page: Pageable,
    ): Page<TransactionListResponseDto> {
        val transactionPage = transactionRepository.findAllByDateBetween(startDate, endDate, page)
        return transactionPage.map { TransactionListResponseDto(it) }
    }

    fun findAllByDateBetween(start: String, end: String, page: Pageable): Page<TransactionListResponseDto> {
        val startDate = LocalDate.parse(start)
        val endDate = LocalDate.parse(end)
        return findAllByDateBetween(startDate, endDate, page)
    }

    fun findAllByDateBetween(startDate: LocalDate, endDate: LocalDate): List<TransactionListResponseDto> {
        val transactionPage = transactionRepository.findAllByDateBetween(startDate, endDate)
        return transactionPage.map { TransactionListResponseDto(it) }
    }

    fun findAllByDateBetween(start: String, end: String): List<TransactionListResponseDto> {
        val startDate = LocalDate.parse(start)
        val endDate = LocalDate.parse(end)
        return findAllByDateBetween(startDate, endDate)
    }
}
