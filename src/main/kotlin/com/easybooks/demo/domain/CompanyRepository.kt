package com.easybooks.demo.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CompanyRepository: JpaRepository<Company, Long> {
    @Query("SELECT c FROM Company c ORDER BY c.id DESC")
    fun findAllDesc(): List<Company>

    fun findByNumber(companyNumber: String): Company?

    @Query("SELECT c FROM Company c WHERE c.name LIKE %:name%")
    fun findByContainName(@Param("name") name: String): List<Company>

    @Query("SELECT c FROM Company c WHERE c.number LIKE %:number%")
    fun findByContainNumber(@Param("number") number: String): List<Company>
}