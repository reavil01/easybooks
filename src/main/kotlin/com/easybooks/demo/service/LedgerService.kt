package com.easybooks.demo.service

import com.easybooks.demo.domain.*
import com.easybooks.demo.web.ledger.dto.LedgerListResponseDto
import com.easybooks.demo.web.ledger.dto.LedgerResponseDto
import com.easybooks.demo.web.ledger.dto.LedgerSaveAndUpdateRequestDto
import com.easybooks.demo.web.ledger.dto.toEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.time.LocalDate

@Service
class LedgerService(
    val ledgerRepository: LedgerRepository,
    val companyRepository: CompanyRepository
) {
    fun save(requestDto: LedgerSaveAndUpdateRequestDto): Long {
        _checkLedgerValidation(requestDto)
        return ledgerRepository.save(requestDto.toEntity(company)).id
    }

    @Transactional
    fun update(id: Long, requestDto: LedgerSaveAndUpdateRequestDto): Long {
        val ledger = ledgerRepository.findById(id)
            ?: throw IllegalArgumentException("해당 송장이 없습니다. id=$id")

        checkingValidationOfLedger(requestDto)
        ledger.update(requestDto)

        return id
    }

    fun findCompanyByNumber(companyNumber: String): Company {
        return companyRepository.findByNumber(companyNumber)
            ?: throw IllegalArgumentException("등록되지 않은 사업자번호입니다. companyNumber=${companyNumber}")
    }

    fun checkingValidationOfLedger(requestDto: LedgerSaveAndUpdateRequestDto) {
        if(requestDto.unitPrice > 0 &&
            requestDto.price != requestDto.unitPrice * requestDto.quantity) {
            throw IllegalArgumentException("단가와 수량의 곱과 가격이 일치하지 않습니다.")
        }

        if(requestDto.price / 10 != requestDto.vat) {
            throw IllegalArgumentException("부가세가 ${requestDto.price} + ${requestDto.vat} 잘못되었습니다.")
        }

        if(requestDto.price + requestDto.vat != requestDto.total) {
            throw IllegalArgumentException("총액이 잘못되었습니다.")
        }
    }

    fun delete(id: Long) {
        val ledger = ledgerRepository.findById(id)
            ?: throw IllegalArgumentException("해당 송장이 없습니다. id=$id")

        ledgerRepository.delete(ledger)
    }

    fun findById(id: Long): LedgerResponseDto {
        val entity = ledgerRepository.findById(id)
            ?: throw IllegalArgumentException("해당 송장이 없습니다. id=$id")

        return LedgerResponseDto(entity)
    }

    fun findAllByCompanyNumberContains(number: String, page: Pageable): Page<LedgerListResponseDto> {
        val ledgerPage = ledgerRepository.findAllByCompanyNumberContains(number, page)
        return ledgerPage.map { LedgerListResponseDto(it) }
    }

    fun findAllByCompanyNameContains(companyName: String, page: Pageable): Page<LedgerListResponseDto> {
        val ledgerPage = ledgerRepository.findAllByCompanyNameContains(companyName, page)
        return ledgerPage.map { LedgerListResponseDto(it) }
    }

    fun findAllByDateBetween(start: LocalDate, end: LocalDate, page: Pageable): Page<LedgerListResponseDto> {
        val ledgerPage = ledgerRepository.findAllByDateBetween(start, end, page)
        return ledgerPage.map { LedgerListResponseDto(it) }
    }
}