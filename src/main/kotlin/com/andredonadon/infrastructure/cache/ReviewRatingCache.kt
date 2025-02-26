package com.andredonadon.infrastructure.cache

import io.quarkus.redis.datasource.RedisDataSource
import io.quarkus.redis.datasource.keys.KeyCommands
import io.quarkus.redis.datasource.value.ValueCommands
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import java.util.UUID

@ApplicationScoped
class ReviewRatingCache(redisDataSource: RedisDataSource) {

    private val cache: ValueCommands<UUID, String> = redisDataSource.value(UUID::class.java, String::class.java)
    private val keyCommands: KeyCommands<UUID> = redisDataSource.key(UUID::class.java)

    fun getReviewRating(restaurantId: UUID): Uni<String?> {
        return Uni.createFrom().item { cache.get(restaurantId) }
    }

    fun setReviewRating(restaurantId: UUID, json: String): Uni<Void> {
        return Uni.createFrom().item {
            cache.set(restaurantId, json)
        }.replaceWithVoid()
    }

    fun invalidateReviewRating(restaurantId: UUID): Uni<Void> {
        return Uni.createFrom().item {
            keyCommands.del(restaurantId)
        }.replaceWithVoid()
    }
}