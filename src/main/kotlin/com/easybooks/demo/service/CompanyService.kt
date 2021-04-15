package com.easybooks.demo.service

import com.easybooks.demo.domain.CompanyRepository
import com.easybooks.demo.domain.update
import com.easybooks.demo.web.dto.CompanyResponseDto
import com.easybooks.demo.web.dto.CompanySaveRequestDto
import com.easybooks.demo.web.dto.CompanyUpdateRequestDto
import com.easybooks.demo.web.dto.toEntity
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import javax.transaction.Transactional

@Service
// 생성자가 하나인 경우 @Autowired 생략해도 injection 됨
class CompanyService (val companyRepository: CompanyRepository) {

    @Transactional
    fun save(requestDto: CompanySaveRequestDto): Long {
        return companyRepository.save(requestDto.toEntity()).id
    }

    @Transactional
    fun update(id: Long, requestDto: CompanyUpdateRequestDto): Long {
        val company = companyRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 사업체가 없습니다. id=$id") }

        company.update(requestDto)

        return id
    }

    @Transactional
    fun delete(id: Long) {
        val company = companyRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 사업체가 없습니다. id=$id") }

        companyRepository.delete(company)
    }

    fun findById(id: Long): CompanyResponseDto {
        val entity = companyRepository.findById(id)
            .orElseThrow{ IllegalArgumentException("해당 사업체가 없습니다. id=$id") }

        return CompanyResponseDto(entity)
    }
}
