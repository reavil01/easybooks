package com.easybooks.demo.web

import com.easybooks.demo.service.CompanyService
import com.easybooks.demo.web.dto.CompanyResponseDto
import com.easybooks.demo.web.dto.CompanySaveRequestDto
import com.easybooks.demo.web.dto.CompanyUpdateRequestDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class CompanyApiController {
    @Autowired
    private lateinit var companyService: CompanyService

    @PostMapping("/api/v1/company")
    fun save(@RequestBody requestDto: CompanySaveRequestDto): Long {
        return companyService.save(requestDto)
    }

    @PostMapping("/api/v1/company/{id}")
    fun update(@PathVariable id: Long,
               @RequestBody requestDto: CompanyUpdateRequestDto): Long {
        return companyService.update(id, requestDto)
    }

    @DeleteMapping("/api/v1/company/{id}")
    fun delete(@PathVariable id: Long): Long {
        companyService.delete(id)
        return id
    }

    @GetMapping("/api/v1/company/{id}")
    fun findById(@PathVariable id: Long): CompanyResponseDto {
        return companyService.findById(id)
    }
}