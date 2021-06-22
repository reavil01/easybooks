package com.easybooks.demo.controller

import com.easybooks.demo.service.CompanyRepositoryService
import com.easybooks.demo.service.PageNavigationService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController(val companyRepositoryService: CompanyRepositoryService) {

    @GetMapping("/")
    fun home(
        @PageableDefault(page = 1, size = 10, sort = ["id"], direction = Sort.Direction.ASC) page: Pageable,
        model: Model
    ): String {
        val baseUrl = "/?"
        val companyPage = companyRepositoryService.findAll(PageNavigationService.convertToZeroBasedPage(page))
        model.addAttribute("companies", companyPage.content)

        val pagenavigationDto = PageNavigationService.getPageNavigationInfo(companyPage, baseUrl)
        model.addAttribute("pagenavigation", pagenavigationDto)

        return "index"
    }
}