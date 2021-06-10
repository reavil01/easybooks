package com.easybooks.demo.web.company

import com.easybooks.demo.service.CompanyService
import com.easybooks.demo.service.PageService
import com.easybooks.demo.service.PageService.addNavigationInfoToModel
import com.easybooks.demo.web.company.dto.NavigationDto
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
    fun searchByName(
        @PathVariable name: String,
        @PageableDefault(page = 1, size = 1, sort = ["id"], direction = Sort.Direction.ASC) page: Pageable,
        model: Model
    ): String {
        val baseUrl = "/company/search/unpaid&name=$name?page="
        model.addAttribute("name", name)
        val companyWithUnpaidDtoPage = companyService.findByNameContains(name, PageService.convertToZeroBasedPage(page))
        model.addAttribute("companys", companyWithUnpaidDtoPage.content)

        val naviDto = NavigationDto(companyWithUnpaidDtoPage, baseUrl)
        addNavigationInfoToModel(model, naviDto)

        return "company-search"
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