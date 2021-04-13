package com.easybooks.demo

enum class LedgerType {
    Sell, Purchase
}

data class Ledger(
    val id: Long,
    val clientId: String,
    val total: Int,
    val type: LedgerType,
)