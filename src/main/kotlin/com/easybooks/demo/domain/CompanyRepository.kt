package com.easybooks.demo.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CompanyRepository {
    fun save(company: Company): Company
    fun delete(company: Company)
    fun deleteAll()
    fun count(): Int

    fun findAll(): List<Company>
    fun findAll(pageable: Pageable): Page<Company>
    fun findById(id: Long): Company?
    fun findByNumber(companyNumber: String): Company?
    fun findAllByOrderByIdDesc(): List<Company>
    fun findAllByNameContains(name: String): List<Company>
    fun findAllByNameContains(name: String, page: Pageable): Page<Company>
    fun findAllByNumberContains(companyNumber: String): List<Company>
    fun findAllByNumberContains(companyNumber: String, page: Pageable): Page<Company>
}