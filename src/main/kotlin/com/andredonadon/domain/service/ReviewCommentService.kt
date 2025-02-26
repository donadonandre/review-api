package com.andredonadon.domain.service


import com.andredonadon.domain.model.Review
import com.andredonadon.domain.repository.ReviewCommentRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class ReviewCommentService(
    private val reviewCommentRepository: ReviewCommentRepository
) {
    fun listReviews(restaurantId: UUID, page: Int, quantity: Int): Uni<List<Review>> {
        return reviewCommentRepository.findReviewsByRestaurant(restaurantId, page, quantity)
    }
}