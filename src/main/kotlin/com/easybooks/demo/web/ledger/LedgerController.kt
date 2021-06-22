package com.easybooks.demo.web.ledger

import com.easybooks.demo.service.LedgerService
import com.easybooks.demo.service.PageService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate

@Controller
@RequestMapping("/ledger")
class LedgerController(val ledgerService: LedgerService) {
    @GetMapping("/search*")
    fun search(
        @RequestParam(name = "companyNumber", required = false) number: String?,
        @RequestParam(name = "companyName", required = false) name: String?,
        @RequestParam(name = "startDate", required = false) startDate: String?,
        @RequestParam(name = "endDate", required = false) endDate: String?,
        @PageableDefault(page = 1, size = 1, sort = ["id"], direction = Sort.Direction.ASC) page: Pageable,
        model: Model
    ): String {
        var baseUrl = "/ledger/search"
        model.addAttribute("number", number)
        model.addAttribute("name", name)
        model.addAttribute("startDate", startDate)
        model.addAttribute("endDate", endDate)

        val ledgerPage = when {
            !number.isNullOrEmpty() -> {
                baseUrl += "?companyNumber=$number"
                ledgerService.findAllByCompanyNumberContains(number, PageService.convertToZeroBasedPage(page))
            }
            !name.isNullOrEmpty() -> {
                baseUrl += "?companyName=$name"
                ledgerService.findAllByCompanyNameContains(name, PageService.convertToZeroBasedPage(page))
            }
            !startDate.isNullOrEmpty() && !endDate.isNullOrEmpty() -> {
                baseUrl += "?startDate=$startDate&endDate=$endDate"
                ledgerService.findAllByDateBetween(startDate, endDate, PageService.convertToZeroBasedPage(page))
            }
            else -> {
                ledgerService.findAll(PageService.convertToZeroBasedPage(page))
            }
        }.apply {
            model.addAttribute("ledgers", this.content)
        }

        PageService.getPageNavigationInfo(ledgerPage, baseUrl).apply {
            model.addAttribute("pagenavigation", this)
        }

        return "ledger-search"
    }

    @GetMapping("/save")
    fun ledgerSave(): String {
        return "ledger-save"
    }

    @GetMapping("/update/{id}")
    fun ledgerUpdate(
        @PathVariable id: Long,
        model: Model
    ): String {
        val dto = ledgerService.findById(id)
        model.addAttribute("ledger", dto)

        return "ledger-update"
    }

}