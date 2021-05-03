package com.easybooks.demo.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CompanyRepository: JpaRepository<Company, Long> {
    fun findAllByOrderByIdDesc(): List<Company>
    fun findByNumber(companyNumber: String): Company?
    fun findByNameContains(name: String): List<Company>
}