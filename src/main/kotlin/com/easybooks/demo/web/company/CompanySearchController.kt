package com.easybooks.demo.web.company

import com.easybooks.demo.service.CompanyService
import com.easybooks.demo.service.PageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/company/search")
class CompanySearchController {
    @Autowired
    lateinit var companyService: CompanyService

    val MAX_VIEW_PAGE_NUMS = 10

    @GetMapping("/unpaid&number={number}")
    fun searchByNumberWithUnpaid(
        @PathVariable number: String,
        model: Model
    ): String {
        model.addAttribute("number", number)
        model.addAttribute("companys", companyService.findByNumberContainsAndUnpaidPrice(number))

        return "company-search"
    }

    @GetMapping("/unpaid&name={name}")
    fun searchByNameWithUnpaid(
        @PathVariable name: String,
        @PageableDefault(page = 1, size = 1, sort = ["id"], direction = Sort.Direction.ASC) page: Pageable,
        model: Model
    ): String {
        val baseUrl = "/company/search/unpaid&name=$name?page="
        model.addAttribute("name", name)
        val companyWithUnpaidDtoPage = companyService.findByNameContains(name, PageService.convertToZeroBasedPage(page))
        model.addAttribute("companys", companyWithUnpaidDtoPage.content)

        val currentPageNum = page.pageNumber - 1
        val startNumOfThisPage = ((currentPageNum / MAX_VIEW_PAGE_NUMS) * MAX_VIEW_PAGE_NUMS) + 1

        val prevUrl = getPrevUrl(startNumOfThisPage, baseUrl)
        val nextUrl = getNextUrl(startNumOfThisPage, companyWithUnpaidDtoPage.totalPages, baseUrl)
        val pageUrls = getPageUrls(startNumOfThisPage, companyWithUnpaidDtoPage.totalPages, baseUrl)
        model.addAttribute("pageUrls", pageUrls)
        model.addAttribute("prevUrl", prevUrl)
        model.addAttribute("nextUrl", nextUrl)

        return "company-search"
    }

    fun getPageUrls(startNumOfThisPage: Int, totalPages: Int, baseUrl: String): List<PageUrl> {
        val minNum = startNumOfThisPage
        val maxNum = if (minNum + MAX_VIEW_PAGE_NUMS < totalPages) minNum + MAX_VIEW_PAGE_NUMS else totalPages
        val pageNums = minNum..maxNum

        return pageNums.map { PageUrl(baseUrl + (it), it) }.toList()
    }

    fun getPrevUrl(startNumOfThisPage: Int, baseUrl: String): String {
        val prevNumCandidate = startNumOfThisPage - 1
        val prevNum = if (prevNumCandidate > 0) prevNumCandidate else 1

        return baseUrl + prevNum
    }

    fun getNextUrl(startNumOfThisPage: Int, totalPages: Int, baseUrl: String): String {
        val nextNumCandidate = startNumOfThisPage + MAX_VIEW_PAGE_NUMS
        val nextNum = if (nextNumCandidate < totalPages) nextNumCandidate else totalPages

        return baseUrl + nextNum
    }

    @GetMapping("/pop={isPopup}")
    fun popupSearchPage(
        @PathVariable isPopup: String,
        model: Model
    ): String {
        model.addAttribute("isPopup", isPopup)

        return "company-search"
    }

}

data class PageUrl(
    val url: String, val pageNum: Int
)