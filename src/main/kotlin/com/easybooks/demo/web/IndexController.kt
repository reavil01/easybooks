package com.easybooks.demo.web

import com.easybooks.demo.service.CompanyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class IndexController {
    @Autowired
    lateinit var companyService: CompanyService

    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("companys", companyService.findAllDesc())
        return "index"
    }

    @GetMapping("/company/save")
    fun companySave(): String {
        return "company-save"
    }

    @GetMapping("/company/update/{id}")
    fun companyUpdate(@PathVariable id: Long,
                      model: Model): String {
        val dto = companyService.findById(id)
        model.addAttribute("company", dto)

        return "company-update"
    }
}