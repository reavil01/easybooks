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

data class SellLedger(
    val id: String,
    val clientId: String,
    val total: Int,
)

data class PurchaseLedger(
    val id: String,
    val clientId: String,
    val total: Int,
)