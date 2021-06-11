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

@Controller
@RequestMapping("/transaction/search")
class TransactionSearchController(
    val transactionService: TransactionService
) {
    @GetMapping("/companyName={companyName}")
    fun findTransactionByCompanyName(
        @PathVariable companyName: String,
        @PageableDefault(page = 1, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable,
        model: Model
    ): String {
        val baseUrl = "/transaction/search/companyName=$companyName"
        model.addAttribute("companyName", companyName)

        val transactionPage =
            transactionService.findAllByCompanyName(companyName, PageService.convertToZeroBasedPage(page))
        model.addAttribute("transactions", transactionPage.content)

        val pagenavigationDto = PageService.getPageNavigationInfo(transactionPage, baseUrl)
        model.addAttribute("pagenavigation", pagenavigationDto)

        return "transaction-search"
    }

    @GetMapping("/companyNumber={companyNumber}")
    fun findTransactionByCompanyNumber(
        @PathVariable companyNumber: String,
        @PageableDefault(page = 1, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable,
        model: Model
    ): String {
        val baseUrl = "/transaction/search/companyNumber=$companyNumber"
        model.addAttribute("companyNumber", companyNumber)

        val transactionPage =
            transactionService.findAllByCompanyNumber(companyNumber, PageService.convertToZeroBasedPage(page))
        model.addAttribute("transactions", transactionPage.content)

        val pagenavigationDto = PageService.getPageNavigationInfo(transactionPage, baseUrl)
        model.addAttribute("pagenavigation", pagenavigationDto)

        return "transaction-search"
    }

    @GetMapping("/startDate={startDate}&endDate={endDate}")
    fun findTransactionBetweenStartDateAndEndDate(
        @PathVariable startDate: String,
        @PathVariable endDate: String,
        @PageableDefault(page = 1, sort = ["id"], direction = Sort.Direction.DESC) page: Pageable,
        model: Model
    ): String {
        val baseUrl = "/transaction/search/startDate=$startDate&endDate=$endDate"
        model.addAttribute("startDate", startDate)
        model.addAttribute("endDate", endDate)

        val transactionPage =
            transactionService.findAllByDateBetween(startDate, endDate, PageService.convertToZeroBasedPage(page))
        model.addAttribute("transactions", transactionPage.content)

        val pagenavigationDto = PageService.getPageNavigationInfo(transactionPage, baseUrl)
        model.addAttribute("pagenavigation", pagenavigationDto)

        return "transaction-search"
    }

    @GetMapping("/transaction/update/{id}")
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
}