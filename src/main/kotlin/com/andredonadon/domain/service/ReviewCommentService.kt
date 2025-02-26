package com.andredonadon.domain.service

import com.andredonadon.domain.model.ReviewComment
import com.andredonadon.domain.repository.ReviewCommentRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import java.util.UUID

@ApplicationScoped
class ReviewCommentService(
    private val reviewCommentRepository: ReviewCommentRepository
) {
    fun listReviews(restaurantId: UUID, page: Int, quantity: Int): Uni<List<ReviewComment>> {
        return reviewCommentRepository.findReviewsByRestaurant(restaurantId, page, quantity)
    }

    fun addReviewComment(restaurantId: UUID, reviewComment: ReviewComment): Uni<Void> {
        return reviewCommentRepository.addReviewComment(restaurantId, reviewComment)
    }
}