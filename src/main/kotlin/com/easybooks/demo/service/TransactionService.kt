package com.easybooks.demo.service

import com.easybooks.demo.domain.Company
import com.easybooks.demo.domain.CompanyRepository
import com.easybooks.demo.domain.TransactionRepository
import com.easybooks.demo.domain.update
import com.easybooks.demo.web.dto.TransactionSaveAndUpdateDto
import com.easybooks.demo.web.dto.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

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
}