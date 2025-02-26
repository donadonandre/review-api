package com.andredonadon.infrastructure.database

import com.andredonadon.domain.model.ReviewRating
import com.andredonadon.domain.repository.ReviewRatingRepository
import com.mongodb.client.model.Filters
import com.mongodb.client.model.UpdateOptions
import com.mongodb.client.model.Updates
import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.quarkus.mongodb.reactive.ReactiveMongoCollection
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class MongoReviewRatingRepository(
    private val mongoClient: ReactiveMongoClient
) : ReviewRatingRepository {

    private fun collection(): ReactiveMongoCollection<ReviewRating> {
        return mongoClient.getDatabase("reviews_db")
            .getCollection("review_ratings", ReviewRating::class.java)
    }

    override fun findByRestaurantId(restaurantId: UUID): Uni<ReviewRating?> {
        return collection()
            .find(Filters.eq("_id", restaurantId))
            .collect()
            .first()
    }

    override fun updateRating(restaurantId: UUID, newRating: Int): Uni<Void> {
        val filter = Filters.eq("_id", restaurantId)

        val update = Updates.combine(
            Updates.inc("totalReviews", 1),
            Updates.set("averageRating", computeNewAverage(restaurantId, newRating))
        )

        return collection()
            .updateOne(filter, update, UpdateOptions().upsert(true))
            .replaceWithVoid()
    }

    private fun computeNewAverage(restaurantId: UUID, newRating: Int): Uni<Double> {
        return findByRestaurantId(restaurantId).map { existing ->
            if (existing == null) newRating.toDouble()
            else ((existing.averageRating * existing.totalReviews) + newRating) / (existing.totalReviews + 1)
        }
    }
}