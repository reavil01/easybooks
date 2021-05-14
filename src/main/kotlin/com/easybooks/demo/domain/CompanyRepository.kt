package com.easybooks.demo.domain

interface CompanyRepository {
    fun save(company: Company): Company
    fun delete(company: Company)
    fun findAll(): List<Company>
    fun deleteAll()
    fun findById(id: Long): Company?

    fun findByNumber(companyNumber: String): Company?
    fun findAllByOrderByIdDesc(): List<Company>
    fun findAllByNameContains(name: String): List<Company>
    fun findAllByNumberContains(companyNumber: String): List<Company>
}