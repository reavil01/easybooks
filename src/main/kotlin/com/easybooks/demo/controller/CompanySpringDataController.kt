package com.easybooks.demo.controller

import com.easybooks.demo.service.CompanyRepositoryService
import com.easybooks.demo.web.dto.company.CompanyResponseDto
import com.easybooks.demo.web.dto.company.CompanySaveRequestDto
import com.easybooks.demo.web.dto.company.CompanyUpdateRequestDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/company")
class CompanySpringDataController(
    val companyRepositoryService: CompanyRepositoryService
) : CompanyDataController {

    @PostMapping
    override fun save(
        @RequestBody requestDto: CompanySaveRequestDto
    ): Long {
        return companyRepositoryService.save(requestDto)
    }

    @PostMapping("/{id}")
    override fun update(
        @PathVariable id: Long,
        @RequestBody requestDto: CompanyUpdateRequestDto
    ): Long {
        return companyRepositoryService.update(id, requestDto)
    }

    @DeleteMapping("/{id}")
    override fun delete(
        @PathVariable id: Long
    ): Long {
        companyRepositoryService.delete(id)
        return id
    }

    @GetMapping("/{id}")
    override fun findById(
        @PathVariable id: Long
    ): CompanyResponseDto {
        return companyRepositoryService.findById(id)
    }
}