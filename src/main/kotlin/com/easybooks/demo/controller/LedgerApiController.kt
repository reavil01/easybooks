package com.easybooks.demo.controller

import com.easybooks.demo.service.LedgerRepositoryService
import com.easybooks.demo.web.dto.ledger.LedgerResponseDto
import com.easybooks.demo.web.dto.ledger.LedgerSaveAndUpdateRequestDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception

@RestController
@RequestMapping("/api/v1/ledger")
class LedgerApiController(val ledgerRepositoryService: LedgerRepositoryService) {
    @PostMapping
    fun save(@RequestBody requestDto: LedgerSaveAndUpdateRequestDto): ResponseEntity<Any> {
        return try {
            val id = ledgerRepositoryService.save(requestDto)
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
        return ledgerRepositoryService.update(id, requestDto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Long {
        ledgerRepositoryService.delete(id)
        return id
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): LedgerResponseDto {
        return ledgerRepositoryService.findById(id)
    }
}