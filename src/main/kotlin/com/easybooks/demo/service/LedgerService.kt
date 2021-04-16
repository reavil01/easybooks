package com.easybooks.demo.service

import com.easybooks.demo.domain.LedgerRepository
import com.easybooks.demo.web.dto.LedgerSaveRequestDto
import com.easybooks.demo.web.dto.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LedgerService(val ledgerRepository: LedgerRepository) {

    @Transactional
    fun save(requestDto: LedgerSaveRequestDto): Long {
        return ledgerRepository.save(requestDto.toEntity()).id
    }

}