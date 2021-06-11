package com.easybooks.demo.web.ledger

import com.easybooks.demo.domain.Ledger
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
import java.time.LocalDate

@Controller
@RequestMapping("/ledger/search")
class LedgerSearchController(val ledgerService: LedgerService) {

    @GetMapping("/companyNumber={number}")
    fun searchByCompanyNumberContains(
        @PathVariable number: String,
        @PageableDefault(page = 1, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable,
        model: Model
    ): String {
        val baseUrl = "/ledger/search/companyNumber=$number?page="
        model.addAttribute("number", number)

        val ledgerPage = ledgerService.findAllByCompanyNumberContains(number, PageService.convertToZeroBasedPage(page))
        model.addAttribute("ledgers", ledgerPage.content)

        val pagenavigationDto = PageService.getPageNavigationInfo(ledgerPage, baseUrl)
        model.addAttribute("pagenavigation", pagenavigationDto)

        return "ledger-search"
    }

    @GetMapping("/companyName={name}")
    fun searchByCompanyNameContains(
        @PathVariable name: String,
        @PageableDefault(page = 1, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable,
        model: Model
    ): String {
        val baseUrl = "/ledger/search/companyName=$name?page="
        model.addAttribute("name", name)

        val ledgerPage = ledgerService.findAllByCompanyNameContains(name, PageService.convertToZeroBasedPage(page))
        model.addAttribute("ledgers", ledgerPage.content)

        val pagenavigationDto = PageService.getPageNavigationInfo(ledgerPage, baseUrl)
        model.addAttribute("pagenavigation", pagenavigationDto)

        return "ledger-search"
    }

    @GetMapping("/startDate={startDate}&endDate={endDate}")
    fun searchBetweenStartDateAndEndDate(
        @PathVariable startDate: String,
        @PathVariable endDate: String,
        @PageableDefault(page = 1, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable,
        model: Model
    ): String {
        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)
        model.addAttribute("startDate", start)
        model.addAttribute("endDate", end)

        val baseUrl = "/ledger/search/startDate=$startDate&endDate=$endDate?page="

        val ledgerPage = ledgerService.findAllByDateBetween(start, end, PageService.convertToZeroBasedPage(page))
        model.addAttribute("ledgers", ledgerPage.content)

        val pagenavigationDto = PageService.getPageNavigationInfo(ledgerPage, baseUrl)
        model.addAttribute("pagenavigation", pagenavigationDto)

        return "ledger-search"
    }

    @GetMapping("/ledger/update/{id}")
    fun ledgerUpdate(
        @PathVariable id: Long,
        model: Model
    ): String {
        val dto = ledgerService.findById(id)
        model.addAttribute("ledger", dto)

        return "ledger-update"
    }

}