package com.easybooks.demo.web

import com.easybooks.demo.domain.Ledger
import com.easybooks.demo.domain.LedgerType
import com.easybooks.demo.domain.Company
import com.easybooks.demo.domain.Transaction
import com.easybooks.demo.domain.TransactionType
import com.easybooks.demo.web.dto.CompanySaveRequestDto
import com.easybooks.demo.web.dto.CompanyUpdateRequestDto
import com.easybooks.demo.web.dto.LedgerSaveAndUpdateRequestDto
import java.time.LocalDate

fun getTestCompany(): Company {
    return Company(
        number = "1234567890",
        name = "페이퍼회사",
        owner = "나",
        address = "어딘가",
        type = "종이",
        items = "종이",
        email = "abc@gmail.com",
        phone = "00000000000",
        fax = "11111111111"
    )
}

fun getTestTransaction(company: Company, paid: Int) = Transaction(
    id = 0,
    company = company,
    date = LocalDate.now(),
    price = paid,
    type = TransactionType.Deposit
)

fun getTestCompanySaveRequestDto() =
    CompanySaveRequestDto(
        number = "1234567890",
        name = "페이퍼회사",
        owner = "나",
        address = "어딘가",
        type = "종이",
        items = "종이",
        email = "abc@gmail.com",
        phone = "00000000000",
        fax = "11111111111"
    )

fun getTestCompanyUpdateRequestDto(
    newNumber: String,
    newName: String
) = CompanyUpdateRequestDto(
    number = newNumber,
    name = newName,
    owner = "나",
    address = "없어요",
    type = "페이퍼",
    items = "종이",
    email = "paper@gmail.com",
    phone = "00000000000",
    fax = "11111111111"
)

fun getTestLedger(company: Company) = Ledger(
    id = 0,
    company = company,
    type = LedgerType.Sell,
    date = LocalDate.now(),
    item = "종이",
    unitPrice = 50,
    quantity = 100,
    price = 5000,
    vat = 500,
    total = 5500
)

fun getTestLedgerSaveRequestDto(companyNumber: String) = LedgerSaveAndUpdateRequestDto(
    companyNumber = companyNumber,
    type = LedgerType.Sell,
    date = LocalDate.now(),
    item = "종이",
    unitPrice = 10,
    quantity = 20,
    price = 200,
    vat = 20,
    total = 220
)