package com.easybooks.demo.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

class ReportServiceTest {
    @Test
    fun getFirstDateAndLastDateWhenGivenYear() {
        val year = "2021"
        val init = LocalDate.now().withYear(year.toInt())
        val firstDate = init.withDayOfYear(1)
        val lastDate = init.withDayOfYear(init.lengthOfYear())

        assertThat(firstDate).isEqualTo("2021-01-01")
        assertThat(lastDate).isEqualTo("2021-12-31")
    }
}