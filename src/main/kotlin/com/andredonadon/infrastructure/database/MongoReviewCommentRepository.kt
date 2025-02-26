package com.andredonadon.infrastructure.database

import com.andredonadon.domain.model.Review
import com.andredonadon.domain.repository.ReviewCommentRepository
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.quarkus.mongodb.reactive.ReactiveMongoCollection
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.bson.Document
import java.util.*

@ApplicationScoped
class MongoReviewCommentRepository(
    private val mongoClient: ReactiveMongoClient
) : ReviewCommentRepository {

    private fun collection(): ReactiveMongoCollection<Review> {
        return mongoClient.getDatabase("reviews_db")
            .getCollection("review_comments", Review::class.java)
    }

    override fun findReviewsByRestaurant(restaurantId: UUID, page: Int, quantity: Int): Uni<List<Review>> {
        val filter = Document()
            .append("restaurantId", restaurantId)
            .append("comment", Document("\$ne", null)) // Equivalente a Filters.ne("comment", null)

        val sort = Document("createdAt", -1) // -1 = DESC, 1 = ASC

        return collection()
            .find(filter)
            .sort(sort)
            .skip((page - 1) * quantity)
            .limit(quantity)
            .collect().asList()
    }
}