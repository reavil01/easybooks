package com.easybooks.demo.web.transaction

import com.easybooks.demo.domain.TransactionType
import com.easybooks.demo.service.PageService
import com.easybooks.demo.service.TransactionService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/transaction")
class TransactionController(
    val transactionService: TransactionService
) {
    @GetMapping("/search*")
    fun search(
        @RequestParam(name = "companyNumber", required = false) number: String?,
        @RequestParam(name = "companyName", required = false) name: String?,
        @RequestParam(name = "startDate", required = false) startDate: String?,
        @RequestParam(name = "endDate", required = false) endDate: String?,
        @PageableDefault(page = 1, size = 1, sort = ["id"], direction = Sort.Direction.ASC) page: Pageable,
        model: Model
    ): String {
        var baseUrl = "/transaction/search"
        model.addAttribute("number", number)
        model.addAttribute("name", name)
        model.addAttribute("startDate", startDate)
        model.addAttribute("endDate", endDate)

        val transactionPage = when {
            number != null -> {
                baseUrl += "?companyNumber=$number"
                transactionService.findAllByCompanyNumberContains(number, PageService.convertToZeroBasedPage(page))
            }
            name != null -> {
                baseUrl += "?companyName=$name"
                transactionService.findAllByCompanyNameContains(name, PageService.convertToZeroBasedPage(page))
            }
            startDate != null && endDate != null -> {
                baseUrl += "?startDate=$startDate&endDate=$endDate"
                transactionService.findAllByDateBetween(startDate, endDate, PageService.convertToZeroBasedPage(page))
            }
            else -> {
                transactionService.findAll(PageService.convertToZeroBasedPage(page))
            }
        }.apply {
            model.addAttribute("transactions", this.content)
        }

        PageService.getPageNavigationInfo(transactionPage, baseUrl).apply {
            model.addAttribute("pagenavigation", this)
        }

        return "transaction-search"
    }

    @GetMapping("/update/{id}")
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

    @GetMapping("/save")
    fun transactionSave(): String {
        return "transaction-save"
    }
}