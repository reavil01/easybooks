package com.easybooks.demo.controller

import com.easybooks.demo.web.dto.company.CompanyResponseDto
import com.easybooks.demo.web.dto.company.CompanySaveRequestDto
import com.easybooks.demo.web.dto.company.CompanyUpdateRequestDto

interface CompanyDataController {

    fun save(requestDto: CompanySaveRequestDto): Long

    fun update(id: Long, requestDto: CompanyUpdateRequestDto): Long

    fun delete(id: Long): Long

    fun findById(id: Long): CompanyResponseDto
}