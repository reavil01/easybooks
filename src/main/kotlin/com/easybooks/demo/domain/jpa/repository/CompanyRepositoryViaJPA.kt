package com.easybooks.demo.domain.jpa.repository

import com.easybooks.demo.domain.Company
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepositoryViaJPA : JpaRepository<Company, Long> {
    fun findByNumber(companyNumber: String): Company?
    fun findAllByOrderByIdDesc(): List<Company>
    fun findAllByNameContains(name: String): List<Company>
    fun findAllByNameContains(name: String, page: Pageable): Page<Company>
    fun findAllByNumberContains(companyNumber: String): List<Company>
    fun findAllByNumberContains(companyNumber: String, page: Pageable): Page<Company>
}