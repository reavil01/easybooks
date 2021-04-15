package com.easybooks.demo.service

import com.easybooks.demo.domain.CompanyRepository
import com.easybooks.demo.web.dto.CompanySaveRequestDto
import com.easybooks.demo.web.dto.toEntity
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
// 생성자가 하나인 경우 @Autowired 생략해도 injection 됨
class CompanyService (val companyRepository: CompanyRepository) {

    @Transactional
    fun save(requestDto: CompanySaveRequestDto): Long {
        return companyRepository.save(requestDto.toEntity()).id
    }
}
