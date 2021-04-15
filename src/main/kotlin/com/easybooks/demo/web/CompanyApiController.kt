package com.easybooks.demo.web

import com.easybooks.demo.service.CompanyService
import com.easybooks.demo.web.dto.CompanySaveRequestDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CompanyApiController {
    @Autowired
    private lateinit var companyService: CompanyService

    @PostMapping("/api/v1/company")
    fun save(@RequestBody requestDto: CompanySaveRequestDto): Long {
        return companyService.save(requestDto)
    }
}