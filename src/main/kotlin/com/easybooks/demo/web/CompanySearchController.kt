package com.easybooks.demo.web

import com.easybooks.demo.service.CompanyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class CompanySearchController {
    @Autowired
    lateinit var companyService: CompanyService

    @GetMapping("/company/search&name={name}")
    fun searchByName(@PathVariable name: String,
                     model: Model): String {
        model.addAttribute("name", name)
        model.addAttribute("companys", companyService.findByNameContains(name))

        return "company-search"
    }

    @GetMapping("/company/search&number={number}")
    fun searchByNumber(@PathVariable number: String,
                     model: Model): String {
        model.addAttribute("number", number)
        model.addAttribute("companys", companyService.findByNumberContainsAndUnpaidPrice(number))

        return "company-search"
    }
}