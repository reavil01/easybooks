package com.easybooks.demo.web.company

import com.easybooks.demo.service.CompanyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/company/search")
class CompanySearchController {
    @Autowired
    lateinit var companyService: CompanyService

    @GetMapping("/name={name}")
    fun searchByName(@PathVariable name: String,
                     model: Model): String {
        model.addAttribute("name", name)
        model.addAttribute("companys", companyService.findByNameContains(name))

        return "company-search"
    }

    @GetMapping("/number={number}")
    fun searchByNumber(@PathVariable number: String,
                     model: Model): String {
        model.addAttribute("number", number)
        model.addAttribute("companys", companyService.findByNumberContains(number))

        return "company-search"
    }

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
        model: Model
    ): String {
        model.addAttribute("name", name)
        model.addAttribute("companys", companyService.findByNameContainsAndUnpaidPrice(name))

        return "company-search"
    }

    @GetMapping("/pop={isPopup}")
    fun popupSearchPage(@PathVariable isPopup: String,
                     model: Model): String {
        model.addAttribute("isPopup", isPopup)

        return "company-search"
    }
}