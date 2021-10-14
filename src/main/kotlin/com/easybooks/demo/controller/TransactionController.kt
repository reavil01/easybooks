package com.easybooks.demo.controller

import com.easybooks.demo.domain.TransactionType
import com.easybooks.demo.service.PageNavigationService
import com.easybooks.demo.service.TransactionRepositoryService
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
    val transactionRepositoryService: TransactionRepositoryService,
) {
    @GetMapping("/search*")
    fun search(
        @RequestParam(name = "companyNumber", required = false) number: String?,
        @RequestParam(name = "companyName", required = false) name: String?,
        @RequestParam(name = "startDate", required = false) startDate: String?,
        @RequestParam(name = "endDate", required = false) endDate: String?,
        @PageableDefault(page = 1, size = 1, sort = ["id"], direction = Sort.Direction.ASC) page: Pageable,
        model: Model,
    ): String {
        var baseUrl = "/transaction/search"
        model.addAttribute("number", number)
        model.addAttribute("name", name)
        model.addAttribute("startDate", startDate)
        model.addAttribute("endDate", endDate)

        val transactionPage = when {
            !number.isNullOrEmpty() -> {
                baseUrl += "?companyNumber=$number"
                transactionRepositoryService.findAllByCompanyNumberContains(
                    number,
                    PageNavigationService.convertToZeroBasedPage(page)
                )
            }
            !name.isNullOrEmpty() -> {
                baseUrl += "?companyName=$name"
                transactionRepositoryService.findAllByCompanyNameContains(
                    name,
                    PageNavigationService.convertToZeroBasedPage(page)
                )
            }
            !startDate.isNullOrEmpty() && !endDate.isNullOrEmpty() -> {
                baseUrl += "?startDate=$startDate&endDate=$endDate"
                transactionRepositoryService.findAllByDateBetween(
                    startDate,
                    endDate,
                    PageNavigationService.convertToZeroBasedPage(page)
                )
            }
            else -> {
                transactionRepositoryService.findAll(PageNavigationService.convertToZeroBasedPage(page))
            }
        }.apply {
            model.addAttribute("transactions", this.content)
        }

        PageNavigationService.getPageNavigationInfo(transactionPage, baseUrl).apply {
            model.addAttribute("pagenavigation", this)
        }

        return "transaction-search"
    }

    @GetMapping("/update/{id}")
    fun transactionUpdate(
        @PathVariable id: Long,
        model: Model,
    ): String {
        val dto = transactionRepositoryService.findById(id)
        model.addAttribute("transaction", dto)
        model.addAttribute("company", dto.company)

        if (dto.type == TransactionType.Withdraw) {
            model.addAttribute("withdrawIsDefault", true)
        }

        return "transaction-update"
    }

    @GetMapping("/save")
    fun transactionSave(): String {
        return "transaction-save"
    }
}