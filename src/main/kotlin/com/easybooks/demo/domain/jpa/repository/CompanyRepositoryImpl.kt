package com.easybooks.demo.domain.jpa.repository

import com.easybooks.demo.domain.Company
import com.easybooks.demo.domain.CompanyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CompanyRepositoryImpl (
    val companyRepoJPA: CompanyRepositoryViaJPA
): CompanyRepository {
    override fun findById(id: Long): Company? {
        return companyRepoJPA.findByIdOrNull(id)
    }

    override fun save(company: Company): Company {
        return companyRepoJPA.save(company)
    }

    override fun delete(company: Company) {
        return companyRepoJPA.delete(company)
    }

    override fun findAll(): List<Company> {
        return companyRepoJPA.findAll()
    }

    override fun deleteAll() {
        return companyRepoJPA.deleteAll()
    }

    override fun findByNumber(companyNumber: String): Company? {
        return companyRepoJPA.findByNumber(companyNumber)
    }

    override fun findAllByOrderByIdDesc(): List<Company> {
        return companyRepoJPA.findAllByOrderByIdDesc()
    }

    override fun findAllByNameContains(name: String): List<Company> {
        return companyRepoJPA.findAllByNameContains(name)
    }

    override fun findAllByNumberContains(companyNumber: String): List<Company> {
        return companyRepoJPA.findAllByNumberContains(companyNumber)
    }
}