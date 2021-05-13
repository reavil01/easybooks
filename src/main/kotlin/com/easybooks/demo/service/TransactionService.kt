package com.easybooks.demo.service

import com.easybooks.demo.domain.*
import com.easybooks.demo.web.dto.TransactionResponseDto
import com.easybooks.demo.web.dto.TransactionSaveAndUpdateDto
import com.easybooks.demo.web.dto.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException
import java.time.LocalDate
import kotlin.streams.toList

@Service
class TransactionService(
    val transactionRepository: TransactionRepository,
    val companyRepository: CompanyRepository
) {
    @Transactional
    fun save(requestDto: TransactionSaveAndUpdateDto): Long {
        val company = getCompanyByCompanyNumber(requestDto.companyNumber)
        val transaction = requestDto.toEntity(company)

        return transactionRepository.save(transaction).id
    }

    @Transactional
    fun update(id: Long, requestDto: TransactionSaveAndUpdateDto): Long {
        getCompanyByCompanyNumber(requestDto.companyNumber)
        val transaction = transactionRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 입/출금 내역이 없습니다 id = $id") }

        transaction.update(requestDto)

        return id
    }

    @Transactional
    fun delete(id: Long) {
        val transaction = transactionRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 입/출금 내역이 없습니다 id = $id") }

        transactionRepository.delete(transaction)
    }

    fun getCompanyByCompanyNumber(companyNumber: String): Company {
        return companyRepository.findByNumber(companyNumber)
            ?: throw IllegalArgumentException("해당 사업체가 없습니다. company number = ${companyNumber}")
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): TransactionResponseDto {
        val entity = transactionRepository.findById(id)
            .orElseThrow { throw IllegalArgumentException("해당 입/출금 내역이 없습니다. transaction id = $id") }

        return TransactionResponseDto(entity)
    }

    @Transactional(readOnly = true)
    fun findByCompanyName(companyName: String): List<Transaction> {
        return transactionRepository.findAllByCompanyNameContains(companyName)
            .stream().toList()
    }

    @Transactional(readOnly = true)
    fun findByCompanyNumber(companyNumber: String): List<Transaction> {
        return transactionRepository.findAllByCompanyNumberContains(companyNumber)
            .stream().toList()
    }

    @Transactional(readOnly = true)
    fun findByDateBetween(startDate: String, endDate: String): List<Transaction> {
        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)
        return transactionRepository.findAllByDateBetween(start, end)
            .stream().toList()
    }
}
