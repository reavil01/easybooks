package com.easybooks.demo.web.transaction

import com.easybooks.demo.domain.TransactionType
import com.easybooks.demo.service.TransactionService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/transaction/search")
class TransactionSearchController(
    val transactionService: TransactionService
) {
    @GetMapping("/companyName={companyName}")
    fun findTransactionByCompanyName(
        @PathVariable companyName: String,
        model: Model
    ): String {
        val transactions = transactionService.findByCompanyName(companyName)
        model.addAttribute("transactions", transactions)

        return "transaction-search"
    }

    @GetMapping("/companyNumber={companyNumber}")
    fun findTransactionByCompanyNumber(
        @PathVariable companyNumber: String,
        model: Model
    ): String {
        val transactions = transactionService.findByCompanyNumber(companyNumber)
        model.addAttribute("transactions", transactions)

        return "transaction-search"
    }

    @GetMapping("/startDate={startDate}&endDate={endDate}")
    fun findTransactionBetweenStartDateAndEndDate(
        @PathVariable startDate: String,
        @PathVariable endDate: String,
        model: Model
    ): String {
        val transactions = transactionService.findByDateBetween(startDate, endDate)
        model.addAttribute("transactions", transactions)

        return "transaction-search"
    }

    @GetMapping("/transaction/update/{id}")
    fun transactionUpdate(
        @PathVariable id: Long,
        model: Model
    ): String {
        val dto = transactionService.findById(id)
        model.addAttribute("transaction", dto)
        when (dto.type) {
            TransactionType.Deposit ->
                model.addAttribute("depositIdDefault", true)
            TransactionType.Withdraw ->
                model.addAttribute("withdrawIsDefault", true)
        }

        return "transaction-update"
    }
}