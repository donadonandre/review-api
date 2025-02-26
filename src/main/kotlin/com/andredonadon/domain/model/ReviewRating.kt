package com.andredonadon.domain.model

import org.bson.codecs.pojo.annotations.BsonId
import java.util.UUID

data class ReviewRating(
    @BsonId
    var restaurantId: UUID,
    var totalReviews: Int = 0,
    var averageRating: Double = 0.0
)
