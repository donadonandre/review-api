package com.andredonadon.domain.service

import com.andredonadon.domain.model.Review
import com.andredonadon.infrastructure.messaging.ReviewProducer
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ReviewService(
    private val reviewProducer: ReviewProducer
) {
    fun saveReview(review: Review): Uni<Void> {
        if (review.rating !in 1..5) {
            return Uni.createFrom().failure(IllegalArgumentException("A nota deve estar entre 1 e 5"))
        }

        reviewProducer.sendReview(review)
        return Uni.createFrom().voidItem()
    }
}