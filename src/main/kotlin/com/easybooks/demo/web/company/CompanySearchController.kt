package com.easybooks.demo.web.company

import com.easybooks.demo.service.CompanyService
import com.easybooks.demo.service.PageService
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

    @GetMapping("/unpaid&number={number}")
    fun searchByNumber(
        @PathVariable number: String,
        @PageableDefault(page = 1, size = 1, sort = ["id"], direction = Sort.Direction.ASC) page: Pageable,
        model: Model
    ): String {
        val baseUrl = "/company/search/unpaid&number=$number?page="
        model.addAttribute("number", number)
        val companyPage = companyService.findByNumberContains(number, PageService.convertToZeroBasedPage(page))
        model.addAttribute("companys", companyPage.content)

        val naviDto = NavigationDto(companyPage, baseUrl)
        addNavigationInfoToModel(model, naviDto)

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
        val companyPage = companyService.findByNameContains(name, PageService.convertToZeroBasedPage(page))
        model.addAttribute("companys", companyPage.content)

        val naviDto = NavigationDto(companyPage, baseUrl)
        addNavigationInfoToModel(model, naviDto)

        return "company-search"
    }

    @GetMapping("/company/update/{id}")
    fun companyUpdate(
        @PathVariable id: Long,
        model: Model
    ): String {
        val dto = companyService.findById(id)
        model.addAttribute("company", dto)

        return "company-update"
    }
}