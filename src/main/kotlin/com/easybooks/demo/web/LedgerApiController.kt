package com.easybooks.demo.web

import com.easybooks.demo.service.LedgerService
import com.easybooks.demo.web.dto.LedgerResponseDto
import com.easybooks.demo.web.dto.LedgerSaveAndUpdateRequestDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception

@RestController
class LedgerApiController (val ledgerService: LedgerService){
    @PostMapping("/api/v1/ledger")
    fun save(@RequestBody requestDto: LedgerSaveAndUpdateRequestDto): ResponseEntity<Any> {
        return try {
            val id = ledgerService.save(requestDto)
            ResponseEntity
                .ok()
                .body(id)
        } catch (e: Exception) {
            ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.message)
        }
    }

    @PostMapping("/api/v1/ledger/{id}")
    fun update(@PathVariable id: Long,
               @RequestBody requestDto: LedgerSaveAndUpdateRequestDto
    ): Long {
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