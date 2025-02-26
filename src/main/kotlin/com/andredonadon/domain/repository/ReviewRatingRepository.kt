package com.andredonadon.domain.repository

import com.andredonadon.domain.model.ReviewRating
import io.smallrye.mutiny.Uni
import java.util.UUID

interface ReviewRatingRepository {
    fun findByRestaurantId(restaurantId: UUID): Uni<ReviewRating?>
    fun updateRating(restaurantId: UUID, newRating: Int): Uni<Void>
}