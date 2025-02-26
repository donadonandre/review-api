package com.andredonadon.application.dto

import java.util.UUID

data class ReviewDTO(
    val restaurantId: UUID,
    val customerId: UUID,
    val customerName: String,
    val rating: Int,
    val comment: String?
)
