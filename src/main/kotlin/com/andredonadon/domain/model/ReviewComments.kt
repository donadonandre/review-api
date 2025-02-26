package com.andredonadon.domain.model

import org.bson.codecs.pojo.annotations.BsonId
import java.time.Instant
import java.util.UUID

data class ReviewComments (
    @BsonId
    var restaurantId: UUID,
    var comments: List<ReviewComment> = listOf()
)

data class ReviewComment(
    var customerId: UUID,
    var customerName: String,
    var rating: Int,
    var comment: String,
    var createdAt: Instant
)