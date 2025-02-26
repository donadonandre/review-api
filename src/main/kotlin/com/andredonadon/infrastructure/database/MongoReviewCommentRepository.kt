package com.andredonadon.infrastructure.database

import com.andredonadon.domain.model.ReviewComment
import com.andredonadon.domain.model.ReviewComments
import com.andredonadon.domain.repository.ReviewCommentRepository
import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.quarkus.mongodb.reactive.ReactiveMongoCollection
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.client.model.UpdateOptions
import java.util.*

@ApplicationScoped
class MongoReviewCommentRepository(
    private val mongoClient: ReactiveMongoClient
) : ReviewCommentRepository {

    private fun collection(): ReactiveMongoCollection<ReviewComments> {
        return mongoClient.getDatabase("reviews_db")
            .getCollection("review_comments", ReviewComments::class.java)
    }

    override fun findReviewsByRestaurant(restaurantId: UUID, page: Int, quantity: Int): Uni<List<ReviewComment>> {
        val skip = (page - 1) * quantity

        return collection()
            .find(Filters.eq("_id", restaurantId))
            .collect().first()
            .map { document ->
                document?.comments
                    ?.sortedByDescending { it.createdAt }
                    ?.drop(skip)
                    ?.take(quantity) ?: listOf()
            }
    }

    override fun addReviewComment(restaurantId: UUID, reviewComment: ReviewComment): Uni<Void> {
        val filter = Filters.eq("_id", restaurantId)
        val update = Updates.push("comments", reviewComment)

        return collection()
            .updateOne(filter, update, UpdateOptions().upsert(true))
            .replaceWithVoid()
    }
}