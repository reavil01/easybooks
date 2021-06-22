package com.easybooks.demo.web.dto.company

import com.easybooks.demo.domain.Company

class CompanyWithUnpaidResponseDto(
    company: Company,
    unpaid: Int,
) {
    val id = company.id
    val number = company.number
    val name = company.name
    val owner = company.owner
    val address = company.address
    val type = company.type
    val items = company.items
    val email = company.email
    val phone = company.phone
    val fax = company.fax
    val unpaid = unpaid
}