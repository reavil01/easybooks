package com.easybooks.demo.service

import com.easybooks.demo.domain.LedgerRepository
import com.easybooks.demo.update
import com.easybooks.demo.web.dto.LedgerResponseDto
import com.easybooks.demo.web.dto.LedgerSaveRequestDto
import com.easybooks.demo.web.dto.LedgerUpdateRequestDto
import com.easybooks.demo.web.dto.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
class LedgerService(val ledgerRepository: LedgerRepository) {

    @Transactional
    fun save(requestDto: LedgerSaveRequestDto): Long {
        return ledgerRepository.save(requestDto.toEntity()).id
    }

    @Transactional
    fun update(id: Long, requestDto: LedgerUpdateRequestDto): Long {
        val ledger = ledgerRepository.findById(id)
            .orElseThrow{ IllegalArgumentException("해당 송장이 없습니다. id=$id") }

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
}