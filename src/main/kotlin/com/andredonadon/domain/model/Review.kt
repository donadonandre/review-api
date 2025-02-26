package com.andredonadon.domain.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant
import java.util.UUID

data class Review(
    @BsonId
    var id: ObjectId? = null,
    var restaurantId: UUID,
    var customerId: UUID,
    var customerName: String,
    var rating: Int,
    var comment: String? = null,
    var createdAt: Instant = Instant.now()
)
