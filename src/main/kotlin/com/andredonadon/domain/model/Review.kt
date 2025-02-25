package com.andredonadon.domain.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Instant

data class Review(
    @BsonId
    var id: ObjectId? = null,
    var restaurantId: String,
    var customerId: String,
    var customerName: String,
    var rating: Int,
    var comment: String? = null,
    var createdAt: Instant = Instant.now()
)
