package com.easybooks.demo.controller

import com.easybooks.demo.service.TransactionRepositoryService
import com.easybooks.demo.web.dto.transaction.TransactionSaveAndUpdateDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/transaction")
class TransactionSpringDataController(
    val transactionRepositoryService: TransactionRepositoryService
) : TransactionDataController {

    @PostMapping
    override fun saveTransaction(
        @RequestBody requestDto: TransactionSaveAndUpdateDto
    ): Long {
        return transactionRepositoryService.save(requestDto)
    }

    @PostMapping("/{id}")
    override fun updateTransaction(
        @PathVariable id: Long,
        @RequestBody requestDto: TransactionSaveAndUpdateDto
    ): Long {
        return transactionRepositoryService.update(id, requestDto)
    }

    @DeleteMapping("/{id}")
    override fun deleteTransaction(
        @PathVariable id: Long
    ): Long {
        transactionRepositoryService.delete(id)
        return id
    }
}