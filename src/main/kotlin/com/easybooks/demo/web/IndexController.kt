package com.easybooks.demo.web

import com.easybooks.demo.service.CompanyService
import com.easybooks.demo.service.LedgerService
import com.easybooks.demo.service.TransactionService
import com.easybooks.demo.web.company.CompanySearchController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {
    @Autowired
    lateinit var companyService: CompanyService

    @Autowired
    lateinit var ledgerService: LedgerService

    @Autowired
    lateinit var transactionService: TransactionService

    @Autowired
    lateinit var companySearchController: CompanySearchController

    @GetMapping("/")
    fun home(
        model: Model,
        @PageableDefault(page = 1, size = 10, sort = ["id"], direction = Sort.Direction.ASC) page: Pageable,
    ): String {
        val baseUrl = "/?page="
        companySearchController.showAllCompanies(page, baseUrl, model)

        return "index"
    }
}