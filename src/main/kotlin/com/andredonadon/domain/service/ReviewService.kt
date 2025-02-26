package com.andredonadon.domain.service

import com.andredonadon.domain.model.Review
import com.andredonadon.domain.repository.ReviewRepository
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ReviewService(
    private val reviewRepository: ReviewRepository
) {
    fun saveReview(review: Review): Uni<Void> {
        if (review.rating !in 1..5) {
            return Uni.createFrom().failure(IllegalArgumentException("A nota deve estar entre 1 e 5"))
        }

        return reviewRepository.findByCustomerAndRestaurant(review.customerId, review.restaurantId)
            .flatMap { existingReview ->
                if (existingReview != null) {
                    Uni.createFrom().failure(IllegalStateException("Cliente já avaliou este restaurante"))
                } else {
                    reviewRepository.save(review)
                }
            }
    }
}