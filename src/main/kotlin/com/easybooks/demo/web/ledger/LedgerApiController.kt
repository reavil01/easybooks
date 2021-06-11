package com.easybooks.demo.web.ledger

import com.easybooks.demo.service.LedgerService
import com.easybooks.demo.web.ledger.dto.LedgerResponseDto
import com.easybooks.demo.web.ledger.dto.LedgerSaveAndUpdateRequestDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception

@RestController
@RequestMapping("/api/v1/ledger")
class LedgerApiController(val ledgerService: LedgerService) {
    @PostMapping
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

    @PostMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody requestDto: LedgerSaveAndUpdateRequestDto
    ): Long {
        return ledgerService.update(id, requestDto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Long {
        ledgerService.delete(id)
        return id
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): LedgerResponseDto {
        return ledgerService.findById(id)
    }
}