package com.easybooks.demo.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CompanyRepository: JpaRepository<Company, Long> {
    @Query("SELECT c FROM Company c ORDER BY c.id DESC")
    fun findAllDesc(): List<Company>
}