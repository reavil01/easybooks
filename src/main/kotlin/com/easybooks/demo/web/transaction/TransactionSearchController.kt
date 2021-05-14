package com.easybooks.demo.web.transaction

import com.easybooks.demo.service.TransactionService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class TransactionSearchController(
    val transactionService: TransactionService
) {
    @GetMapping("/transaction/search&companyName={companyName}")
    fun findTransactionByCompanyName(
        @PathVariable companyName: String,
        model: Model
    ): String {
        val transactions = transactionService.findByCompanyName(companyName)
        model.addAttribute("transactions", transactions)

        return "transaction-search"
    }

    @GetMapping("/transaction/search&companyNumber={companyNumber}")
    fun findTransactionByCompanyNumber(
        @PathVariable companyNumber: String,
        model: Model
    ): String {
        val transactions = transactionService.findByCompanyNumber(companyNumber)
        model.addAttribute("transactions", transactions)

        return "transaction-search"
    }

    @GetMapping("/transaction/search&startDate={startDate}&endDate={endDate}")
    fun findTransactionBetweenStartDateAndEndDate(
        @PathVariable startDate: String,
        @PathVariable endDate: String,
        model: Model
    ): String {
        val transactions = transactionService.findByDateBetween(startDate, endDate)
        model.addAttribute("transactions", transactions)

        return "transaction-search"
    }
}