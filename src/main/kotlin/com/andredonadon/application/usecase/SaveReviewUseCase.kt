package com.andredonadon.application.usecase

import com.andredonadon.application.dto.ReviewDTO
import com.andredonadon.application.mapper.ReviewMapper
import com.andredonadon.domain.service.ReviewService
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class SaveReviewUseCase(
    private val reviewService: ReviewService,
    private val reviewMapper: ReviewMapper
) {
    fun execute(reviewDTO: ReviewDTO): Uni<Void> {
        val review = reviewMapper.toDomain(reviewDTO)
        return reviewService.saveReview(review)
    }
}