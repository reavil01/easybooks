package com.easybooks.demo.domain

import com.easybooks.demo.web.dto.CompanyUpdateRequestDto
import net.bytebuddy.implementation.bind.annotation.AllArguments
import javax.persistence.*

@Entity
class Company (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(length = 20, nullable = false, unique = true)
    var number: String,

    @Column(length = 20, nullable = false)
    var name: String,

    @Column(length = 20, nullable = false)
    var owner: String,

    @Column(nullable = false)
    var address: String,

    @Column(nullable = false)
    var type: String,

    @Column(nullable = false)
    var items: String,

    @Column(nullable = false)
    var email: String,

    @Column(length = 20)
    var phone: String = "",       // 11111111111 (하이픈 없이)

    @Column(length = 20)
    var fax: String = "",    // 11111111111 (하이픈 없이)
)

fun Company.update(requestDto: CompanyUpdateRequestDto) {
    number = requestDto.number
    name = requestDto.name
    owner = requestDto.owner
    address = requestDto.address
    type = requestDto.type
    items = requestDto.items
    email = requestDto.email
    phone = requestDto.phone
    fax = requestDto.fax
}