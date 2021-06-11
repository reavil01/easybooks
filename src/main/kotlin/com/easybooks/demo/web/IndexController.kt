package com.easybooks.demo.web

import com.easybooks.demo.service.CompanyService
import com.easybooks.demo.service.PageService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController(val companyService: CompanyService) {

    @GetMapping("/")
    fun home(
        @PageableDefault(page = 1, size = 10, sort = ["id"], direction = Sort.Direction.ASC) page: Pageable,
        model: Model
    ): String {
        val baseUrl = "/?page="
        val companyPage = companyService.findAll(PageService.convertToZeroBasedPage(page))
        model.addAttribute("companies", companyPage.content)

        val pagenavigationDto = PageService.getPageNavigationInfo(companyPage, baseUrl)
        model.addAttribute("pagenavigation", pagenavigationDto)

        return "index"
    }
}