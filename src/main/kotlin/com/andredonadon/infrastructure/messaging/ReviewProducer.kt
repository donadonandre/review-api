package com.andredonadon.infrastructure.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import com.andredonadon.domain.model.Review
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter

@ApplicationScoped
class ReviewProducer(
    private val objectMapper: ObjectMapper,
    @Channel("reviews") private val emitter: Emitter<String>
) {
    fun sendReview(review: Review) {
        val json = objectMapper.writeValueAsString(review)
        emitter.send(json)
    }
}