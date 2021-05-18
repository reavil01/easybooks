package com.easybooks.demo.web

import com.easybooks.demo.domain.TransactionType
import com.easybooks.demo.service.CompanyService
import com.easybooks.demo.service.LedgerService
import com.easybooks.demo.service.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@Controller
class IndexController {
    @Autowired
    lateinit var companyService: CompanyService
    @Autowired
    lateinit var ledgerService: LedgerService
    @Autowired
    lateinit var transactionService: TransactionService

    @GetMapping("/")
    fun index(
        model: Model,
        @RequestParam("page", defaultValue = "1") pageNum: Int,
    ): String {
        model.addAttribute("companys", companyService.getCompanyListWithUnpaid(pageNum))
        val (prevNum, nextNum) = companyService.getPrevNextNum(pageNum)
        model.addAttribute("prev", prevNum)
        model.addAttribute("next", nextNum)
        val pageNums = companyService.getPageNums(pageNum)
        model.addAttribute("pageNums", pageNums)
        model.addAttribute("ledgers", ledgerService.findAllDesc())
        return "index"
    }

    @GetMapping("/company/save")
    fun companySave(): String {
        return "company-save"
    }

    @GetMapping("/company/search")
    fun companySearch(
        @RequestParam("page", defaultValue = "1") pageNum: Int,
        model: Model
    ): String{
        val companys = companyService.getCompanyListWithUnpaid(pageNum)
        model.addAttribute("companys", companys)

        return "company-search"
    }

    @GetMapping("/ledger/save")
    fun ledgerSave(): String {
        return "ledger-save"
    }

    @GetMapping("/ledger/search")
    fun ledgerSearch(): String {
        return "ledger-search"
    }

    @GetMapping("/company/update/{id}")
    fun companyUpdate(@PathVariable id: Long,
                      model: Model): String {
        val dto = companyService.findById(id)
        model.addAttribute("company", dto)

        return "company-update"
    }

    @GetMapping("/ledger/update/{id}")
    fun ledgerUpdate(@PathVariable id: Long,
                     model: Model): String {
        val dto = ledgerService.findById(id)
        model.addAttribute("ledger", dto)

        return "ledger-update"
    }

    @GetMapping("/transaction/save")
    fun transactionSave(): String {
        return "transaction-save"
    }

    @GetMapping("/transaction/update/{id}")
    fun transactionUpdate(@PathVariable id: Long,
                     model: Model): String {
        val dto = transactionService.findById(id)
        model.addAttribute("transaction", dto)
        when(dto.type) {
            TransactionType.Deposit ->
                model.addAttribute("depositIdDefault", true)
            TransactionType.Withdraw ->
                model.addAttribute("withdrawIsDefault", true)
        }

        return "transaction-update"
    }

    @GetMapping("/transaction/search")
    fun transactionSearch(): String {
        return "transaction-search"
    }
}