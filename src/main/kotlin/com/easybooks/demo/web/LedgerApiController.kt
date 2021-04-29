package com.easybooks.demo.web

import com.easybooks.demo.service.LedgerService
import com.easybooks.demo.web.dto.LedgerResponseDto
import com.easybooks.demo.web.dto.LedgerSaveRequestDto
import com.easybooks.demo.web.dto.LedgerUpdateRequestDto
import org.springframework.web.bind.annotation.*

@RestController
class LedgerApiController (val ledgerService: LedgerService){
    @PostMapping("/api/v1/ledger")
    fun save(@RequestBody requestDto: LedgerSaveRequestDto): Long {
        return ledgerService.save(requestDto)
    }

    @PostMapping("/api/v1/ledger/{id}")
    fun update(@PathVariable id: Long,
               @RequestBody requestDto: LedgerUpdateRequestDto): Long {
        return ledgerService.update(id, requestDto)
    }

    @DeleteMapping("/api/v1/ledger/{id}")
    fun delete(@PathVariable id: Long): Long {
        ledgerService.delete(id)
        return id
    }

    @GetMapping("/api/v1/ledger/{id}")
    fun findById(@PathVariable id: Long): LedgerResponseDto {
        return ledgerService.findById(id)
    }
}