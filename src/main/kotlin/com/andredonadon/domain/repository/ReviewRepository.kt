package com.andredonadon.domain.repository

import com.andredonadon.domain.model.Review
import io.smallrye.mutiny.Uni
import java.util.UUID

interface ReviewRepository {
    fun save(review: Review): Uni<Void>
    fun findByCustomerAndRestaurant(customerId: UUID, restaurantId: UUID): Uni<Review?>
}