package com.andredonadon.infrastructure.database

import com.andredonadon.domain.model.Review
import com.andredonadon.domain.repository.ReviewRepository
import com.mongodb.client.model.Filters
import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.quarkus.mongodb.reactive.ReactiveMongoCollection
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class MongoReviewRepository(
    private val mongoClient: ReactiveMongoClient
) : ReviewRepository {

    private fun collection(): ReactiveMongoCollection<Review> {
        return mongoClient.getDatabase("reviews_db")
            .getCollection("reviews", Review::class.java)
    }

    override fun save(review: Review): Uni<Void> {
        return collection().insertOne(review).replaceWithVoid()
    }

    override fun findByCustomerAndRestaurant(customerId: UUID, restaurantId: UUID): Uni<Review?> {
        val filter = Filters.and(
            Filters.eq("customerId", customerId),
            Filters.eq("restaurantId", restaurantId)
        )

        return collection()
            .find(filter)
            .collect()
            .first()
    }
}