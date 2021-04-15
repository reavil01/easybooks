package com.easybooks.demo.domain

import net.bytebuddy.implementation.bind.annotation.AllArguments
import javax.persistence.*

@Entity
class Company (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(length = 20, nullable = false)
    val number: String,

    @Column(length = 20, nullable = false)
    val name: String,

    @Column(length = 20, nullable = false)
    val owner: String,

    @Column(nullable = false)
    val address: String,

    @Column(nullable = false)
    val type: String,

    @Column(nullable = false)
    val items: String,

    @Column(nullable = false)
    val email: String,

    @Column(length = 20)
    val phone: String = "",       // 11111111111 (하이픈 없이)

    @Column(length = 20)
    val fax: String = "",    // 11111111111 (하이픈 없이)
)