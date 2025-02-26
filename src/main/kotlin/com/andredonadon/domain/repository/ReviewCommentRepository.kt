package com.andredonadon.domain.repository

import com.andredonadon.domain.model.ReviewComment
import io.smallrye.mutiny.Uni
import java.util.*

interface ReviewCommentRepository {
    fun findReviewsByRestaurant(restaurantId: UUID, page: Int, quantity: Int): Uni<List<ReviewComment>>
    fun addReviewComment(restaurantId: UUID, reviewComment: ReviewComment): Uni<Void>
}