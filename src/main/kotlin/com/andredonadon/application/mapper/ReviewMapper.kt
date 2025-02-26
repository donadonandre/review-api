package com.andredonadon.application.mapper


import com.andredonadon.application.dto.ReviewDTO
import com.andredonadon.domain.model.Review
import jakarta.enterprise.context.ApplicationScoped
import java.time.Instant

@ApplicationScoped
class ReviewMapper {
    fun toDomain(dto: ReviewDTO): Review {
        return Review(
            restaurantId = dto.restaurantId,
            customerId = dto.customerId,
            customerName = dto.customerName,
            rating = dto.rating,
            comment = dto.comment,
            createdAt = Instant.now()
        )
    }
}