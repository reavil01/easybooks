package com.easybooks.demo.controller

import com.easybooks.demo.service.TransactionRepositoryService
import com.easybooks.demo.web.dto.transaction.TransactionSaveAndUpdateDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/transaction")
class TransactionApiController(
    val transactionRepositoryService: TransactionRepositoryService
) {
    @PostMapping
    fun saveTransaction(@RequestBody requestDto: TransactionSaveAndUpdateDto): Long {
        return transactionRepositoryService.save(requestDto)
    }

    @PostMapping("/{id}")
    fun updateTransaction(
        @PathVariable id: Long,
        @RequestBody requestDto: TransactionSaveAndUpdateDto
    ): Long {
        return transactionRepositoryService.update(id, requestDto)
    }

    @DeleteMapping("/{id}")
    fun deleteTransaction(@PathVariable id: Long): Long {
        transactionRepositoryService.delete(id)
        return id
    }
}