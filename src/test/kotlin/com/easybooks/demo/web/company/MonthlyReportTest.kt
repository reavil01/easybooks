package com.easybooks.demo.web.company

import com.easybooks.demo.domain.*
import com.easybooks.demo.service.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

class MonthlyReportTest {
    @Autowired
    lateinit var ledgerRepository: LedgerRepository

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @Autowired
    lateinit var reportService: ReportService

    @Autowired
    lateinit var ledgerService: LedgerService

    @Autowired
    lateinit var transactionService: TransactionService

    @Test
    fun `람다 groupingBy 기능 테스트`() {
        val ledgers = ledgerRepository.findAll()
        val a = ledgers
            .groupingBy { it.company.number }
            .aggregate { _, acc: Int?, ledger, _ ->
                when (ledger.type) {
                    LedgerType.Sell -> (acc ?: 0) + ledger.total
                    LedgerType.Purchase -> (acc ?: 0) - ledger.total
                }
            }

        println(a.keys)
        println(a.values)

        val b = a.asIterable().fold(0) { acc, entry ->
            acc + entry.value
        }

        println(b)
    }

    @Test
    fun `집계기능 확인`() {
        val (startDate, endDate) = reportService.getFirstDateAndLastDateWhenGivenYear(2021)

        val ledgers = ledgerService.findAllByDateBetween(startDate, endDate)
        val transactions = transactionService.findAllByDateBetween(startDate, endDate)

        val result = reportService.yearSummaryReport(ledgers, transactions)

        assertThat(result.totalLedger).isEqualTo(7)
        assertThat(result.totalTransaction).isEqualTo(5)
        assertThat(result.totalSell).isEqualTo(11110)
        assertThat(result.totalBuy).isEqualTo(9955)
        assertThat(result.totalReceived).isEqualTo(300)
        assertThat(result.totalUnreceived).isEqualTo(200)
    }

    @Test
    fun `사업체별 집계 기능 확인`() {
        val summary = reportService.getYearlyReport("2021")
        for(report in summary.companyReport) {
            println(report)
        }
    }

    @Test
    fun `두 개의 grouping된 결과물을 key값을 이용해 join하기`() {
        val ledgers = ledgerRepository.findAll()
        val transactions = transactionRepository.findAll()

        val a = ledgers
            .groupingBy { it.company.number }
            .foldTo(mutableMapOf(), 0) { acc, e ->
                acc + e.total
            }
        println(a.keys)
        println(a.values)
        val b = transactions.groupingBy { it.company.number }
            .foldTo(mutableMapOf(), 0) {
                acc, e -> acc + e.price
            }

        println(b.keys)
        println(b.values)


//        val ledgerGroup = ledgerRepository.findAll().groupingBy { it.company.number }
//        val transactionGroup = transactionRepository.findAll().groupingBy{it.company.number}
//        println(setOf(ledgerGroup))
    }

    @Test
    fun `두 개 이상의 리스트에서 전체 key 목록 추출하기`() {
        val a = HashMap<String, Int>()
        a["1"] = 1
        a["2"] = 2
        val b = HashMap<String, String>()
        b["1"] = "1"
        b["3"] = "3"

        val ss = HashSet<String>()
        ss.addAll(a.keys)
        ss.addAll(b.keys)
        println(ss)
    }

//    @LocalServerPort
//    lateinit var port: java.lang.Integer
//
//    @Autowired
//    lateinit var reportService: ReportService
//
//    @Autowired
//    lateinit var companyService: CompanyService
//
//
//    @Autowired
//    lateinit var transactionService: TransactionService
//
//    @Test
//    fun `사업체별 월별 요약정보 표기에 성공`() {
//        // given
//        val savedForReportTransactions = ArrayList<TransactionSaveAndUpdateDto>()
//        savedForReportTransactions.add(
//            TransactionSaveAndUpdateDto(
//                companyNumber = "111",
//                date = LocalDate.of(2021, 6, 1),
//                price = 1,
//                type = TransactionType.Deposit
//            )
//        )
//        savedForReportTransactions.add(
//            TransactionSaveAndUpdateDto(
//                companyNumber = "111",
//                date = LocalDate.of(2021, 6, 2),
//                price = 10,
//                type = TransactionType.Deposit
//            )
//        )
//        savedForReportTransactions.add(
//            TransactionSaveAndUpdateDto(
//                companyNumber = "111",
//                date = LocalDate.of(2021, 6, 30),
//                price = 100,
//                type = TransactionType.Deposit
//            )
//        )
//
//        val savedForExceptionTransaction = ArrayList<TransactionSaveAndUpdateDto>()
//        savedForExceptionTransaction.add(
//            TransactionSaveAndUpdateDto(
//                companyNumber = "111",
//                date = LocalDate.of(2021, 5, 31),
//                price = 1000,
//                type = TransactionType.Deposit
//            )
//        )
//        savedForExceptionTransaction.add(
//            TransactionSaveAndUpdateDto(
//                companyNumber = "111",
//                date = LocalDate.of(2021, 7, 1),
//                price = 10000,
//                type = TransactionType.Deposit
//            )
//        )
//        savedForExceptionTransaction.add(
//            TransactionSaveAndUpdateDto(
//                companyNumber = "111",
//                date = LocalDate.of(2021, 6, 31),
//                price = 100000,
//                type = TransactionType.Deposit
//            )
//        )
//
//        savedForReportTransactions.forEach { transactionService.save(it) }
//        savedForExceptionTransaction.forEach { transactionService.save(it) }
//
//        // when
//        val year = 2021
//        val month = 6
//        val report = reportService.getMonthlyReport(yesr, month)
//
//         then
//        assertThat(report.unpaid).isEqualTo()
//        assertThat(report.content)
//    }

}