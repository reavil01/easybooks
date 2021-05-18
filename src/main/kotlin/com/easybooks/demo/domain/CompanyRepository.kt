package com.easybooks.demo.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CompanyRepository {
    fun save(company: Company): Company
    fun delete(company: Company)
    fun findAll(): List<Company>
    fun deleteAll()
    fun findById(id: Long): Company?
    fun count(): Int

    fun findByNumber(companyNumber: String): Company?
    fun findAllByOrderByIdDesc(): List<Company>
    fun findAllByNameContains(name: String): List<Company>
    fun findAllByNumberContains(companyNumber: String): List<Company>

    fun findAll(pageable: Pageable): Page<Company>
}