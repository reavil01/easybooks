package com.easybooks.demo.web

import com.easybooks.demo.service.CompanyService
import com.easybooks.demo.service.LedgerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class IndexController {
    @Autowired
    lateinit var companyService: CompanyService
    @Autowired
    lateinit var ledgerService: LedgerService

    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("companys", companyService.findAllDesc())
        model.addAttribute("ledgers", ledgerService.findAllDesc())
        return "index"
    }

    @GetMapping("/company/save")
    fun companySave(): String {
        return "company-save"
    }

    @GetMapping("/company/update/{id}")
    fun companyUpdate(@PathVariable id: Long,
                      model: Model): String {
        val dto = companyService.findById(id)
        model.addAttribute("company", dto)

        return "company-update"
    }

    @GetMapping("/ledger/save")
    fun ledgerSave(): String {
        return "ledger-save"
    }

    @GetMapping("/ledger/update/{id}")
    fun ledgerUpdate(@PathVariable id: Long,
                     model: Model): String {
        val dto = ledgerService.findById(id)
        model.addAttribute("ledger", dto)

        return "ledger-update"
    }

    @GetMapping("/company/search")
    fun companySearch(): String{
        return "company-search"
    }
}