package com.andredonadon.infrastructure.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import com.andredonadon.domain.model.Review
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter


@ApplicationScoped
class ReviewKafkaProducer(
    private val objectMapper: ObjectMapper,
    @Channel("restaurant-review-saver") private val emitter: Emitter<String>
) {
    fun send(review: Review) {
        val json = objectMapper.writeValueAsString(review)
        emitter.send(json)
    }
}