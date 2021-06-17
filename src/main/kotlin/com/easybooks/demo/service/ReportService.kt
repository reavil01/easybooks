package com.easybooks.demo.service

import com.easybooks.demo.domain.LedgerType
import com.easybooks.demo.domain.TransactionType
import com.easybooks.demo.web.ledger.dto.LedgerListResponseDto
import com.easybooks.demo.web.transaction.dto.TransactionListResponseDto
import org.springframework.stereotype.Service
import java.time.LocalDate

/**
 * 연간 전체 리포트
 * 요약정보
 * 총 ledger 개수, 총 transaction 개수
 * 총 매출/매입 금액, 총 수금/미수금 금액
 *
 * 상세정보(회사별로)
 * 회사명, 총 ledger 개수, 총 transaction 개수, 매출/매입 금액, 수금/미수금 금액
 * -ledger 건수를 누르면 해당 기간, 해당 사업자의 거래 목록을 보여주는 페이지로
 * -transaction 건수를 누르면 해당 기간, 해당 사업자의 입/출금 목록을 보여주는 페이지로
 */
@Service
class ReportService(
    val ledgerService: LedgerService,
    val transactionService: TransactionService,
    val companyService: CompanyService
) {
    fun getYearlyReport(year: String): Report {
        val (startDate, endDate) = getFirstDateAndLastDateWhenGivenYear(year.toInt())

        val ledgers = ledgerService.findAllByDateBetween(startDate, endDate)
        val transactions = transactionService.findAllByDateBetween(startDate, endDate)


        val summary = yearSummaryReport(ledgers, transactions)

        val companyBuying = getTotalBuyingPriceOfEachCompany(ledgers)
        val companySelling = getTotalSellingPriceOfEachComapny(ledgers)
        val companyReceive = getTotalReceivedOfEachCompany(transactions)
        val companyUnreceive = getTotalUnreceivedOfEachCompany(transactions)

        val numOfTransaction = transactions.groupingBy { it.company.number }.eachCount()
        val numOfLeger = ledgers.groupingBy { it.company.number }.eachCount()

        val totalKeys = HashSet<String>()
        totalKeys.addAll(companyBuying.keys)
        totalKeys.addAll(companySelling.keys)
        totalKeys.addAll(companyReceive.keys)
        totalKeys.addAll(companyUnreceive.keys)

        val companySummary = HashMap<String, SummaryReport>()
        for (key in totalKeys) {
            companySummary.put(
                key,
                SummaryReport(
                    totalLedger = numOfLeger[key] ?: 0,
                    totalTransaction = numOfTransaction[key] ?: 0,
                    totalSell = companySelling[key] ?: 0,
                    totalBuy = companyBuying[key] ?: 0,
                    totalReceived = companyReceive[key] ?: 0,
                    totalUnreceived = companyUnreceive[key] ?: 0
                )
            )
        }
        val report = Report(
            year = 0,
            month = 0,
            summary = summary,
            companyReport = companySummary
        )

        return report
    }

    fun yearSummaryReport(
        ledgers: List<LedgerListResponseDto>,
        transactions: List<TransactionListResponseDto>
    ): SummaryReport {
        val totalLedger = ledgers.size
        val totalBuy =
            getTotalBuyingPriceOfEachCompany(ledgers).asIterable().fold(0) { acc, entry -> acc + entry.value }
        val totalSell =
            getTotalSellingPriceOfEachComapny(ledgers).asIterable().fold(0) { acc, entry -> acc + entry.value }

        val totalTransaction = transactions.size
        val totalReceived =
            getTotalReceivedOfEachCompany(transactions).asIterable().fold(0) { acc, entry -> acc + entry.value }
        val totalUnreceived =
            getTotalUnreceivedOfEachCompany(transactions).asIterable().fold(0) { acc, entry -> acc + entry.value }

        val summary = SummaryReport(
            totalLedger = totalLedger,
            totalTransaction = totalTransaction,
            totalSell = totalSell,
            totalBuy = totalBuy,
            totalReceived = totalReceived,
            totalUnreceived = totalUnreceived
        )
        return summary
    }

    private fun getTotalReceivedOfEachCompany(transactions: List<TransactionListResponseDto>) =
        transactions.filter { it.type == TransactionType.Deposit }
            .groupingBy { it.company.number }
            .aggregate { _, deposit: Int?, transaction, _ -> (deposit ?: 0) + transaction.price }

    private fun getTotalUnreceivedOfEachCompany(transactions: List<TransactionListResponseDto>) =
        transactions.filter { it.type == TransactionType.Withdraw }
            .groupingBy { it.company.number }
            .aggregate { _, withdraw: Int?, transaction, _ -> (withdraw ?: 0) + transaction.price }

    private fun getTotalBuyingPriceOfEachCompany(ledgers: List<LedgerListResponseDto>) =
        ledgers.filter { it.type == LedgerType.Purchase }
            .groupingBy { it.company.number }
            .aggregate { _, buy: Int?, ledger, _ -> (buy ?: 0) + ledger.total }

    private fun getTotalSellingPriceOfEachComapny(ledgers: List<LedgerListResponseDto>) =
        ledgers.filter { it.type == LedgerType.Sell }
            .groupingBy { it.company.number }
            .aggregate { _, sell: Int?, ledger, _ -> (sell ?: 0) + ledger.total }

    fun getFirstDateAndLastDateWhenGivenYear(year: Int): Pair<LocalDate, LocalDate> {
        val initDate = LocalDate.now().withYear(year)
        val firstDate = initDate.withDayOfYear(1)
        val lastDate = initDate.withDayOfYear(initDate.lengthOfYear())
        return firstDate to lastDate
    }
}


data class Report(
    val year: Int,
    val month: Int,
    val summary: SummaryReport,
    val companyReport: HashMap<String, SummaryReport>
)

data class SummaryReport(
    val totalLedger: Int,
    val totalTransaction: Int,
    val totalSell: Int,
    val totalBuy: Int,
    val totalReceived: Int,
    val totalUnreceived: Int
)