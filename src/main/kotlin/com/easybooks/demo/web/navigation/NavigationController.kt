package com.easybooks.demo.web.navigation

import com.easybooks.demo.service.CompanyService
import com.easybooks.demo.service.PageService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class NavigationController(val companyService: CompanyService) {


    @GetMapping("/transaction/save")
    fun transactionSave(): String {
        return "transaction-save"
    }

    @GetMapping("/transaction/search")
    fun transactionSearch(model: Model): String {
        return "transaction-search"
    }

}