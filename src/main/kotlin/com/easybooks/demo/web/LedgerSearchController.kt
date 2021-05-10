package com.easybooks.demo.web

import com.easybooks.demo.service.LedgerService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class LedgerSearchController(val ledgerService: LedgerService) {
    @GetMapping("/ledger/search&companyName={name}")
    fun searchByCompanyNameContains(@PathVariable name: String,
                    model: Model): String {
        model.addAttribute("name", name)
        model.addAttribute("ledgers", ledgerService.findAllByCompanyNameContains(name))

        return "ledger-search"
    }
}