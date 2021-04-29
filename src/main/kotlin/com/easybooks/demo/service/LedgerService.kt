package com.easybooks.demo.service

import com.easybooks.demo.domain.CompanyRepository
import com.easybooks.demo.domain.LedgerRepository
import com.easybooks.demo.update
import com.easybooks.demo.web.dto.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException
import java.util.stream.Collectors

@Service
class LedgerService(val ledgerRepository: LedgerRepository, val companyRepository: CompanyRepository) {
    @Transactional
    fun save(requestDto: LedgerSaveRequestDto): Long {
        companyRepository.findByNumber(requestDto.companyNumber)
            ?: throw IllegalArgumentException("등록되지 않은 사업자번호입니다. companyNumber=${requestDto.companyNumber}")

        return ledgerRepository.save(requestDto.toEntity()).id
    }

    @Transactional
    fun update(id: Long, requestDto: LedgerUpdateRequestDto): Long {
        val ledger = ledgerRepository.findById(id)
            .orElseThrow{ IllegalArgumentException("해당 송장이 없습니다. id=$id") }

        companyRepository.findByNumber(requestDto.companyNumber)
            ?: throw IllegalArgumentException("등록되지 않은 사업자번호입니다. companyNumber=${requestDto.companyNumber}")

        ledger.update(requestDto)

        return id
    }

    @Transactional
    fun delete(id: Long) {
        val ledger = ledgerRepository.findById(id)
            .orElseThrow{ IllegalArgumentException("해당 송장이 없습니다. id=$id") }

        ledgerRepository.delete(ledger)
    }

    fun findById(id: Long): LedgerResponseDto {
        val entity = ledgerRepository.findById(id)
            .orElseThrow{ IllegalArgumentException("해당 송장이 없습니다. id=$id") }

        return LedgerResponseDto(entity)
    }

    @Transactional(readOnly = true)
    fun findAllDesc(): List<LedgerListResponseDto> {
        return ledgerRepository.findAllDesc().stream()
            .map{LedgerListResponseDto(it)}
            .collect(Collectors.toList())
    }
}