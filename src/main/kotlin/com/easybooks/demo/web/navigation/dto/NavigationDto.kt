package com.easybooks.demo.web.navigation.dto

import com.easybooks.demo.service.PageService
import org.springframework.data.domain.Page

data class NavigationDto(
    val page: Page<*>,
    val baseUrl: String,
) {
    private val currentPageNum = page.number
    private val startNumOfThisPage = PageService.getStartNumOfThisPage(currentPageNum)
    val prevUrl = PageService.getPrevUrl(startNumOfThisPage, baseUrl)
    val nextUrl = PageService.getNextUrl(startNumOfThisPage, page.totalPages, baseUrl)
    val pageUrls = PageService.getPageUrls(startNumOfThisPage, page.totalPages, baseUrl)
}