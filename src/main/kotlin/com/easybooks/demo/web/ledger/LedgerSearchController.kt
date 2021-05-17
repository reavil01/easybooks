package com.easybooks.demo.web.ledger

import com.easybooks.demo.service.LedgerService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDate

@Controller
@RequestMapping("/ledger/search")
class LedgerSearchController(val ledgerService: LedgerService) {
    @GetMapping("/companyNumber={number}")
    fun searchByCompanyNumberContains(@PathVariable number: String,
                                    model: Model): String {
        model.addAttribute("number", number)
        model.addAttribute("ledgers", ledgerService.findAllByCompanyNumberContains(number))

        return "ledger-search"
    }

    @GetMapping("/companyName={name}")
    fun searchByCompanyNameContains(@PathVariable name: String,
                    model: Model): String {
        model.addAttribute("name", name)
        model.addAttribute("ledgers", ledgerService.findAllByCompanyNameContains(name))

        return "ledger-search"
    }

    @GetMapping("/startDate={startDate}&endDate={endDate}")
    fun searchBetweenStartDateAndEndDate(
        @PathVariable startDate: String,
        @PathVariable endDate: String,
        model: Model
    ): String {
        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)

        model.addAttribute("startDate", start)
        model.addAttribute("endDate", end)
        model.addAttribute("ledgers", ledgerService.findAllByDateBetween(start, end))

        return "ledger-search"
    }
}