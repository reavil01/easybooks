package com.easybooks.demo.service

import com.easybooks.demo.domain.*
import com.easybooks.demo.web.dto.company.toEntity
import com.easybooks.demo.web.dto.ledger.LedgerListResponseDto
import com.easybooks.demo.web.dto.ledger.LedgerResponseDto
import com.easybooks.demo.web.dto.ledger.LedgerSaveAndUpdateRequestDto
import com.easybooks.demo.web.dto.ledger.toEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import java.time.LocalDate

@Service
class LedgerRepositoryService(
    val ledgerRepository: LedgerRepository,
    val companyRepositoryService: CompanyRepositoryService,
    val ledgerValidatorService: LedgerValidatorService
) {
    fun save(requestDto: LedgerSaveAndUpdateRequestDto): Long {
        val company = companyRepositoryService.findByNumber(requestDto.companyNumber).toEntity()
        ledgerValidatorService.checkingValidationOfLedger(requestDto)
        return ledgerRepository.save(requestDto.toEntity(company)).id
    }

    fun update(id: Long, requestDto: LedgerSaveAndUpdateRequestDto): Long {
        val ledger = ledgerRepository.findById(id)
            ?: throw IllegalArgumentException("해당 송장이 없습니다. id=$id")

        ledgerValidatorService.checkingValidationOfLedger(requestDto)
        ledger.update(requestDto)
        ledgerRepository.save(ledger)

        return id
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

    fun findAllByDateBetween(start: String, end: String, page: Pageable): Page<LedgerListResponseDto> {
        val startDate = LocalDate.parse(start)
        val endDate = LocalDate.parse(end)
        return findAllByDateBetween(startDate, endDate, page)
    }

    fun findAllByDateBetween(startDate: LocalDate, endDate: LocalDate, page: Pageable): Page<LedgerListResponseDto> {
        val ledgerPage = ledgerRepository.findAllByDateBetween(startDate, endDate, page)
        return ledgerPage.map { LedgerListResponseDto(it) }
    }

    fun findAllByDateBetween(start: String, end: String): List<LedgerListResponseDto> {
        val startDate = LocalDate.parse(start)
        val endDate = LocalDate.parse(end)
        return findAllByDateBetween(startDate, endDate)
    }

    fun findAllByDateBetween(startDate: LocalDate, endDate: LocalDate): List<LedgerListResponseDto> {
        val ledgers = ledgerRepository.findAllByDateBetween(startDate, endDate)
        return ledgers.map { LedgerListResponseDto(it) }
    }

    fun findAll(page: Pageable): Page<LedgerListResponseDto> {
        val ledgerPage = ledgerRepository.findAll(page)
        return ledgerPage.map { LedgerListResponseDto(it) }
    }

}