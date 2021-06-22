package com.easybooks.demo.web.dto.navigation

import com.easybooks.demo.service.PageUrl

data class NavigationDto(
    val prevUrl: String,
    val nextUrl: String,
    val pageUrls: List<PageUrl>
)