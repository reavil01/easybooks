package com.easybooks.demo.controller

import com.easybooks.demo.service.ReportService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate

@Controller
@RequestMapping("/report")
class ReportController(val reportService: ReportService) {
    @GetMapping()
    fun report(
        @RequestParam(name = "year", required = false) year: String?,
        @RequestParam(name = "month", required = false) month: String?,
        model: Model,
    ): String {
        val selectYear = when (year) {
            null -> LocalDate.now().year
            else -> year
        }.toString()
        val selectMonth = when (month) {
            null -> LocalDate.now().monthValue
            else -> month
        }.toString()

        val yearData = reportService.getYearData(selectYear)
        model.addAttribute("yearData", yearData)
        val monthData = reportService.getMonthData(selectMonth)
        model.addAttribute("monthData", monthData)

        var report = reportService.getReport(selectYear, selectMonth)
        model.addAttribute("report", report)

        return "report"
    }

}