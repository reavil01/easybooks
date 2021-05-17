package com.easybooks.demo.web.company

import com.easybooks.demo.service.CompanyService
import com.easybooks.demo.web.company.dto.CompanyResponseDto
import com.easybooks.demo.web.company.dto.CompanySaveRequestDto
import com.easybooks.demo.web.company.dto.CompanyUpdateRequestDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/company")
class CompanyApiController {
    @Autowired
    private lateinit var companyService: CompanyService

    @PostMapping
    fun save(@RequestBody requestDto: CompanySaveRequestDto): Long {
        return companyService.save(requestDto)
    }

    @PostMapping("/{id}")
    fun update(@PathVariable id: Long,
               @RequestBody requestDto: CompanyUpdateRequestDto): Long {
        return companyService.update(id, requestDto)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): Long {
        companyService.delete(id)
        return id
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): CompanyResponseDto {
        return companyService.findById(id)
    }
}