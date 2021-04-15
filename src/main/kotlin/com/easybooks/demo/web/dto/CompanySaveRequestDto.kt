package com.easybooks.demo.web.dto

import com.easybooks.demo.domain.Company

class CompanySaveRequestDto (
    val number: String,
    val name: String,
    val owner: String,
    val address: String,
    val type: String,
    val items: String,
    val email: String,
    val phone: String = "",
    val fax: String = "",
)

fun CompanySaveRequestDto.toEntity() = Company(
    id = 0,
    number = number,
    name = name,
    owner = owner,
    address = address,
    type = type,
    items = items,
    email = email,
    phone = phone,
    fax = fax
)