package com.easybooks.demo.service

import com.easybooks.demo.web.dto.navigation.NavigationDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

object PageNavigationService {
    private const val MAX_VIEW_PAGE_NUMS = 10

    fun convertToZeroBasedPage(page: Pageable): Pageable {
        return if (page.pageNumber <= 0) {
            PageRequest.of(0, page.pageSize, page.sort)
        } else {
            PageRequest.of(page.pageNumber - 1, page.pageSize, page.sort)
        }
    }

    private fun getStartNumOfThisPage(currentPageNum: Int): Int {
        return ((currentPageNum / MAX_VIEW_PAGE_NUMS) * MAX_VIEW_PAGE_NUMS) + 1
    }

    private fun getPageUrls(startNumOfThisPage: Int, totalPages: Int, baseUrl: String): List<PageUrl> {
        val minNum = startNumOfThisPage
        val maxNum = if (minNum + MAX_VIEW_PAGE_NUMS < totalPages) minNum + MAX_VIEW_PAGE_NUMS else totalPages + 1
        val pageNums = minNum until maxNum

        return pageNums.map { PageUrl(baseUrl + (it), it) }.toList()
    }

    private fun getPrevUrl(startNumOfThisPage: Int, baseUrl: String): String {
        val prevNumCandidate = startNumOfThisPage - 1
        val prevNum = if (prevNumCandidate > 0) prevNumCandidate else 1

        return baseUrl + prevNum
    }

    private fun getNextUrl(startNumOfThisPage: Int, totalPages: Int, baseUrl: String): String {
        val nextNumCandidate = startNumOfThisPage + MAX_VIEW_PAGE_NUMS
        val nextNum = if (nextNumCandidate < totalPages) nextNumCandidate else totalPages

        return baseUrl + nextNum
    }

    fun getPageNavigationInfo(page: Page<*>, baseUrl: String): NavigationDto {
        val basePageUrl = "$baseUrl&page="
        val currentPageNum = page.number
        val startNumOfThisPage = getStartNumOfThisPage(currentPageNum)
        val prevUrl = getPrevUrl(startNumOfThisPage, basePageUrl)
        val nextUrl = getNextUrl(startNumOfThisPage, page.totalPages, basePageUrl)
        val pageUrls = getPageUrls(startNumOfThisPage, page.totalPages, basePageUrl)

        return NavigationDto(prevUrl, nextUrl, pageUrls)
    }
}

data class PageUrl(
    val url: String, val pageNum: Int
)