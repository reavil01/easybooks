package com.easybooks.demo.web.company

import com.easybooks.demo.service.CompanyService
import com.easybooks.demo.service.PageService
import com.easybooks.demo.web.navigation.dto.NavigationDto
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/company")
class CompanyController(val companyService: CompanyService) {
    @GetMapping("/search*")
    fun search(
        @RequestParam(name = "number", required = false) number: String?,
        @RequestParam(name = "name", required = false) name: String?,
        @RequestParam(name = "popup", required = false, defaultValue = "false") isPopup: Boolean,
        @PageableDefault(page = 1, size = 1, sort = ["id"], direction = Sort.Direction.ASC) page: Pageable,
        model: Model
    ): String {
        var baseUrl = "/company/search?popup=$isPopup"
        model.addAttribute("number", number)
        model.addAttribute("name", name)
        model.addAttribute("isPopup", isPopup)

        val companyPage = when {
            number != null -> {
                baseUrl += "&number=$number"
                companyService.findByNumberContains(number, PageService.convertToZeroBasedPage(page))
            }
            name != null -> {
                baseUrl += "&name=$name"
                companyService.findByNameContains(name, PageService.convertToZeroBasedPage(page))
            }
            else -> {
                companyService.findAll(PageService.convertToZeroBasedPage(page))
            }
        }.apply {
            addContentOnModel(model, this.content)
        }

        PageService.getPageNavigationInfo(companyPage, baseUrl).apply {
            addPagenavigationOnModel(model, this)
        }
        return "company-search"
    }

    private fun addContentOnModel(model: Model, content: List<*>) {
        model.addAttribute("companies", content)
    }

    private fun addPagenavigationOnModel(model: Model, pagenavigationDto: NavigationDto) {
        model.addAttribute("pagenavigation", pagenavigationDto)
    }

    @GetMapping("/update/{id}")
    fun companyUpdate(
        @PathVariable id: Long,
        model: Model
    ): String {
        val dto = companyService.findById(id)
        model.addAttribute("company", dto)

        return "company-update"
    }

    @GetMapping("/save")
    fun companySave(): String {
        return "company-save"
    }
}