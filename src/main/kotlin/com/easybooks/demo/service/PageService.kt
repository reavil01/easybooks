package com.easybooks.demo.service

import com.easybooks.demo.web.company.PageUrl
import com.easybooks.demo.web.company.dto.NavigationDto
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.ui.Model

object PageService {
    private const val MAX_VIEW_PAGE_NUMS = 10

    fun convertToZeroBasedPage(page: Pageable): Pageable {
        return if (page.pageNumber <= 0) {
            PageRequest.of(0, page.pageSize, page.sort)
        } else {
            PageRequest.of(page.pageNumber - 1, page.pageSize, page.sort)
        }
    }

    fun getStartNumOfThisPage(currentPageNum: Int): Int {
        return ((currentPageNum / MAX_VIEW_PAGE_NUMS) * MAX_VIEW_PAGE_NUMS) + 1
    }

    fun getPageUrls(startNumOfThisPage: Int, totalPages: Int, baseUrl: String): List<PageUrl> {
        val minNum = startNumOfThisPage
        val maxNum = if (minNum + MAX_VIEW_PAGE_NUMS < totalPages) minNum + MAX_VIEW_PAGE_NUMS else totalPages + 1
        val pageNums = minNum until maxNum

        return pageNums.map { PageUrl(baseUrl + (it), it) }.toList()
    }

    fun getPrevUrl(startNumOfThisPage: Int, baseUrl: String): String {
        val prevNumCandidate = startNumOfThisPage - 1
        val prevNum = if (prevNumCandidate > 0) prevNumCandidate else 1

        return baseUrl + prevNum
    }

    fun getNextUrl(startNumOfThisPage: Int, totalPages: Int, baseUrl: String): String {
        val nextNumCandidate = startNumOfThisPage + MAX_VIEW_PAGE_NUMS
        val nextNum = if (nextNumCandidate < totalPages) nextNumCandidate else totalPages

        return baseUrl + nextNum
    }

    fun addNavigationInfoToModel(model: Model, navigationDto: NavigationDto): Model {
        model.addAttribute("pageUrls", navigationDto.pageUrls)
        model.addAttribute("prevUrl", navigationDto.prevUrl)
        model.addAttribute("nextUrl", navigationDto.nextUrl)
        return model
    }
}