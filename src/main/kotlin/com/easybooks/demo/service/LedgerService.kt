package com.easybooks.demo.service

import com.easybooks.demo.domain.*
import com.easybooks.demo.web.dto.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.util.stream.Collectors
import kotlin.streams.toList

@Service
class LedgerService(
    val ledgerRepository: LedgerRepository,
    val companyRepository: CompanyRepository
) {
    @Transactional
    fun save(requestDto: LedgerSaveAndUpdateRequestDto): Long {
        _checkLedgerValidation(requestDto)
        return ledgerRepository.save(requestDto.toEntity(company)).id
    }

    @Transactional
    fun update(id: Long, requestDto: LedgerSaveAndUpdateRequestDto): Long {
        val ledger = ledgerRepository.findById(id)
            ?: throw IllegalArgumentException("해당 송장이 없습니다. id=$id")

        _checkLedgerValidation(requestDto)
        ledger.update(requestDto)

        return id
    }

    fun findCompanyByNumber(companyNumber: String): Company {
        return companyRepository.findByNumber(companyNumber)
            ?: throw IllegalArgumentException("등록되지 않은 사업자번호입니다. companyNumber=${companyNumber}")
    }

    fun _checkLedgerValidation(requestDto: LedgerSaveAndUpdateRequestDto) {
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

    @Transactional
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

    @Transactional(readOnly = true)
    fun findAllDesc(): List<LedgerListResponseDto> {
        return ledgerRepository.findAllByOrderByIdDesc().stream()
            .map{LedgerListResponseDto(it)}
            .collect(Collectors.toList())
    }

    @Transactional(readOnly = true)
    fun findAllByCompanyNameContains(companyName: String): List<LedgerListResponseDto> {
        return ledgerRepository.findAllByCompanyNameContains(companyName)
            .stream()
            .map{ LedgerListResponseDto(it) }
            .toList()
    }

    @Transactional(readOnly = true)
    fun findAllByDateBetween(start: LocalDate, end: LocalDate): List<LedgerListResponseDto> {
        return ledgerRepository.findAllByDateBetween(start, end)
            .stream()
            .map{ LedgerListResponseDto(it) }
            .toList()
    }

    @Transactional(readOnly = true)
    fun findAllByCompanyNumberContains(number: String): List<LedgerListResponseDto> {
        return ledgerRepository.findAllByCompanyNumberContains(number)
            .stream()
            .map{ LedgerListResponseDto(it) }
            .toList()
    }
}