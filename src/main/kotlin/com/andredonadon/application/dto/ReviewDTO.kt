package com.andredonadon.application.dto

data class ReviewDTO(
    var restaurantId: String,
    var customerName: String,
    var rating: Int,
    var comment: String?,
)
