package com.andredonadon.domain.repository

import com.andredonadon.domain.model.Review
import io.smallrye.mutiny.Uni
import java.util.UUID

interface ReviewCommentRepository {
    fun findReviewsByRestaurant(restaurantId: UUID, page: Int, quantity: Int): Uni<List<Review>>
}