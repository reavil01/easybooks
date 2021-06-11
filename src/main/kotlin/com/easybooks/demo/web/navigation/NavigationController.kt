package com.easybooks.demo.web.navigation

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class NavigationController {
    @GetMapping("/ledger/save")
    fun ledgerSave(): String {
        return "ledger-save"
    }

    @GetMapping("/ledger/search")
    fun ledgerSearch(): String {
        return "ledger-search"
    }

    @GetMapping("/transaction/save")
    fun transactionSave(): String {
        return "transaction-save"
    }

    @GetMapping("/transaction/search")
    fun transactionSearch(): String {
        return "transaction-search"
    }

    @GetMapping("/company/save")
    fun companySave(): String {
        return "company-save"
    }

    @GetMapping("/company/search")
    fun companySearch(
        model: Model
    ): String {
//        val companys = companyService.getCompanyListWithUnpaid(pageNum)
//        model.addAttribute("companys", companys)
//        val (prevNum, nextNum) = companyService.getPrevNextNum(pageNum)
//        model.addAttribute("prev", prevNum)
//        model.addAttribute("next", nextNum)
//        val pageNums = companyService.getPageNums(pageNum)
//        model.addAttribute("pageNums", pageNums)
//        model.addAttribute("ledgers", ledgerService.findAllDesc())
        return "company-search"
    }
}