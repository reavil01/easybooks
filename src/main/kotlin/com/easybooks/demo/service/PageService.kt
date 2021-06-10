package com.easybooks.demo.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

object PageService {
    fun convertToZeroBasedPage(page: Pageable): Pageable {
        return if (page.pageNumber <= 0) {
            PageRequest.of(0, page.pageSize, page.sort)
        } else {
            PageRequest.of(page.pageNumber-1, page.pageSize, page.sort)
        }
    }
}