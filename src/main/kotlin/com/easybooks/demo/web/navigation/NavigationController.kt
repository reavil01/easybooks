package com.easybooks.demo.web.navigation

import com.easybooks.demo.service.CompanyService
import com.easybooks.demo.service.PageService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class NavigationController(val companyService: CompanyService) {

    @GetMapping("/ledger/save")
    fun ledgerSave(): String {
        return "ledger-save"
    }

    @GetMapping("/ledger/search")
    fun ledgerSearch(model: Model): String {
        return "ledger-search"
    }

    @GetMapping("/transaction/save")
    fun transactionSave(): String {
        return "transaction-save"
    }

    @GetMapping("/transaction/search")
    fun transactionSearch(model: Model): String {
        return "transaction-search"
    }

    @GetMapping("/company/save")
    fun companySave(): String {
        return "company-save"
    }

    @GetMapping("/company/search")
    fun companySearch(
        @PageableDefault(page = 1, size = 10, sort = ["id"], direction = Sort.Direction.ASC) page: Pageable,
        model: Model
    ): String {
        val baseUrl = "/company/search/?page="
        val companyPage = companyService.findAll(PageService.convertToZeroBasedPage(page))
        model.addAttribute("companies", companyPage.content)

        val pagenavigationDto = PageService.getPageNavigationInfo(companyPage, baseUrl)
        model.addAttribute("pagenavigation", pagenavigationDto)

        return "company-search"
    }
}