package com.easybooks.demo.domain.jpa.repository

import com.easybooks.demo.domain.Company
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepositoryViaJPA : JpaRepository<Company, Long> {
    fun findByNumber(companyNumber: String): Company?
    fun findAllByOrderByIdDesc(): List<Company>
    fun findAllByNameContains(name: String): List<Company>
    fun findAllByNumberContains(companyNumber: String): List<Company>
}