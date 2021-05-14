package com.easybooks.demo.web.transaction

import com.easybooks.demo.service.TransactionService
import com.easybooks.demo.web.transaction.dto.TransactionSaveAndUpdateDto
import org.springframework.web.bind.annotation.*

@RestController
class TransactionApiController(
    val transactionService: TransactionService
) {
    @PostMapping("/api/v1/transaction")
    fun saveTransaction(@RequestBody requestDto: TransactionSaveAndUpdateDto): Long {
        return transactionService.save(requestDto)
    }

    @PostMapping("/api/v1/transaction/{id}")
    fun updateTransaction(@PathVariable id: Long,
        @RequestBody requestDto: TransactionSaveAndUpdateDto): Long {
        return transactionService.update(id, requestDto)
    }

    @DeleteMapping("/api/v1/transaction/{id}")
    fun deleteTransaction(@PathVariable id: Long): Long {
        transactionService.delete(id)
        return id
    }
}