package com.easybooks.demo.controller

import com.easybooks.demo.web.dto.ledger.LedgerResponseDto
import com.easybooks.demo.web.dto.ledger.LedgerSaveAndUpdateRequestDto
import org.springframework.http.ResponseEntity

interface LedgerDataController {

    fun save(requestDto: LedgerSaveAndUpdateRequestDto): ResponseEntity<Any>

    fun update(id: Long, requestDto: LedgerSaveAndUpdateRequestDto): Long

    fun delete(id: Long): Long

    fun findById(id: Long): LedgerResponseDto
}