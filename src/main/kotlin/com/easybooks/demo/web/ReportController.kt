package com.easybooks.demo.web

import com.easybooks.demo.service.ReportService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/report")
class ReportController(val reportService: ReportService) {
    @GetMapping()
    fun report(
        @RequestParam(name = "year", required = false) year: String?,
        @RequestParam(name = "month", required = false) month: String?,
        model: Model
    ): String {
        val yearData = reportService.getYearData(year)
        model.addAttribute("yearData", yearData)
        val monthData = reportService.getMonthData(month)
        model.addAttribute("monthData", monthData)

        if(year != null) {
            var report = reportService.getYearlyReport(year)
            model.addAttribute("report", report)
            println(report)
        }
        return "report"
    }

}