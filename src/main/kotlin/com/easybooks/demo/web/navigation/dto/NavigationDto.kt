package com.easybooks.demo.web.navigation.dto

import com.easybooks.demo.service.PageUrl

data class NavigationDto(
    val prevUrl: String,
    val nextUrl: String,
    val pageUrls: List<PageUrl>
)