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

    private fun getLedgers(year: String): List<LedgerListResponseDto> {
        val (startDate, endDate) = getFirstDateAndLastDate(year.toInt())
        return ledgerService.findAllByDateBetween(startDate, endDate)
    }

    private fun getLedgers(year: String, month: String) {

    }

    private fun getTransactions(year: String): List<TransactionListResponseDto> {
        val (startDate, endDate) = getFirstDateAndLastDate(year.toInt())
        return transactionService.findAllByDateBetween(startDate, endDate)

    }

    private fun getTransactions(year: String, month: String) {

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
        val totalPaid =
            getTotalPaidOfEachCompany(transactions).asIterable().fold(0) { acc, entry -> acc + entry.value }

        val summary = SummaryReport(
            totalLedger = totalLedger,
            totalTransaction = totalTransaction,
            totalSell = totalSell,
            totalBuy = totalBuy,
            totalReceived = totalReceived,
            totalUnreceived = totalSell - totalReceived,
            totalPaid = totalPaid,
            totalUnpaid = totalBuy - totalPaid,
        )
        return summary
    }

    fun companySummaryReport(year: String): List<CompanySummaryReport> {
        val (startDate, endDate) = getFirstDateAndLastDate(year.toInt())

        val ledgers = ledgerService.findAllByDateBetween(startDate, endDate)
        val transactions = transactionService.findAllByDateBetween(startDate, endDate)

        val companyBuying = getTotalBuyingPriceOfEachCompany(ledgers)
        val companySelling = getTotalSellingPriceOfEachComapny(ledgers)
        val companyReceive = getTotalReceivedOfEachCompany(transactions)
        val companyPaid = getTotalPaidOfEachCompany(transactions)
        val companyNumOfTransaction = transactions.groupingBy { it.company.id }.eachCount()
        val companyNumOfLeger = ledgers.groupingBy { it.company.id }.eachCount()

        val totalKeys = HashSet<Long>()
        totalKeys.addAll(companyBuying.keys)
        totalKeys.addAll(companySelling.keys)
        totalKeys.addAll(companyReceive.keys)
        totalKeys.addAll(companyPaid.keys)

        val companySummary = ArrayList<CompanySummaryReport>()
        for (key in totalKeys) {
            val companyResponseDto =  companyService.findById(key)

            companySummary.add(
                CompanySummaryReport(
                    company = companyResponseDto,
                    totalLedger = companyNumOfLeger[key] ?: 0,
                    totalTransaction = companyNumOfTransaction[key] ?: 0,
                    totalSell = companySelling[key] ?: 0,
                    totalBuy = companyBuying[key] ?: 0,
                    totalReceived = companyReceive[key] ?: 0,
                    totalUnreceived = (companySelling[key] ?: 0) - (companyReceive[key] ?: 0),
                    totalPaid = companyPaid[key] ?: 0,
                    totalUnpaid = (companyBuying[key] ?: 0) - (companyPaid[key] ?: 0)
                )
            )
        }

        return companySummary
    }
    private fun getTotalReceivedOfEachCompany(transactions: List<TransactionListResponseDto>) =
        transactions.filter { it.type == TransactionType.Deposit }
            .groupingBy { it.company.id }
            .aggregate { _, deposit: Int?, transaction, _ -> (deposit ?: 0) + transaction.price }

    private fun getTotalPaidOfEachCompany(transactions: List<TransactionListResponseDto>) =
        transactions.filter { it.type == TransactionType.Withdraw }
            .groupingBy { it.company.id }
            .aggregate { _, withdraw: Int?, transaction, _ -> (withdraw ?: 0) + transaction.price }

    private fun getTotalBuyingPriceOfEachCompany(ledgers: List<LedgerListResponseDto>) =
        ledgers.filter { it.type == LedgerType.Purchase }
            .groupingBy { it.company.id }
            .aggregate { _, buy: Int?, ledger, _ -> (buy ?: 0) + ledger.total }

    private fun getTotalSellingPriceOfEachComapny(ledgers: List<LedgerListResponseDto>) =
        ledgers.filter { it.type == LedgerType.Sell }
            .groupingBy { it.company.id }
            .aggregate { _, sell: Int?, ledger, _ -> (sell ?: 0) + ledger.total }

    fun getFirstDateAndLastDate(year: Int): Pair<LocalDate, LocalDate> {
        val initDate = LocalDate.now().withYear(year)
        val firstDate = initDate.withDayOfYear(1)
        val lastDate = initDate.withDayOfYear(initDate.lengthOfYear())
        return firstDate to lastDate
    }

    fun getFirstDateAndLastDate(year: Int, month: Int): Pair<LocalDate, LocalDate> {
        val initDate = LocalDate.now().withYear(year).withMonth(month)
        val firstDate = initDate.withDayOfMonth(1)
        val lastDate = initDate.withDayOfMonth(initDate.lengthOfMonth())
        return firstDate to lastDate
    }

    fun getYearData(selectYear: String?): List<YearDto> {
        val currentYear = LocalDate.now().year
        val yearDtoList = ArrayList<YearDto>()

        for(year in currentYear-5..currentYear+5) {
            if(selectYear?.toInt() == year)
                yearDtoList.add(YearDto(year, year.toString(), true))
            else
                yearDtoList.add(YearDto(year, year.toString()))
        }

        return yearDtoList
    }

    fun getMonthData(selectMonth: String?): List<MonthDto> {
        val monthDtoList = ArrayList<MonthDto>()

        for(month in 1..12) {
            if(selectMonth?.toInt() == month)
                monthDtoList.add(MonthDto(month, month.toString()+"월", true))
            else
                monthDtoList.add(MonthDto(month, month.toString()+"월"))
        }

        return monthDtoList
    }
}

data class YearDto(
    val value: Int,
    val text: String,
    val select: Boolean = false
)

data class MonthDto(
    val value: Int,
    val text: String,
    val select: Boolean = false
)

data class Report(
    val year: Int,
    val month: Int,
    val summary: SummaryReport,
    val companyReport: List<CompanySummaryReport>
)

data class SummaryReport(
    val totalLedger: Int,
    val totalTransaction: Int,
    val totalSell: Int,
    val totalBuy: Int,
    val totalReceived: Int,
    val totalUnreceived: Int,
    val totalPaid: Int,
    val totalUnpaid: Int,
)
data class CompanySummaryReport(
    val company: CompanyResponseDto,
    val totalLedger: Int,
    val totalTransaction: Int,
    val totalSell: Int,
    val totalBuy: Int,
    val totalReceived: Int,
    val totalUnreceived: Int,
    val totalPaid: Int,
    val totalUnpaid: Int,
)