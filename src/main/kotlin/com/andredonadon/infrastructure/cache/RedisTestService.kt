package com.andredonadon.infrastructure.cache

import io.quarkus.redis.datasource.RedisDataSource
import io.quarkus.redis.datasource.keys.KeyCommands
import io.quarkus.redis.datasource.value.ValueCommands
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import java.util.UUID

@ApplicationScoped
class RedisTestService(redisDataSource: RedisDataSource) {

    private val cache: ValueCommands<UUID, String> = redisDataSource.value(UUID::class.java, String::class.java)
    private val keyCommands: KeyCommands<UUID> = redisDataSource.key(UUID::class.java) // ✅ Criando KeyCommands para deletar

    fun setKey(key: UUID, value: String): Uni<Void> {
        return Uni.createFrom().item {
            cache.set(key, value)  // ✅ Agora `set()` está correto
        }.replaceWithVoid()
    }

    fun getKey(key: UUID): Uni<String?> {
        return Uni.createFrom().item { cache.get(key) } // ✅ Agora `get()` está correto
    }

    fun invalidateKey(key: UUID): Uni<Void> {
        return Uni.createFrom().item {
            keyCommands.del(key)  // ✅ Agora `del()` está correto e remove a chave do Redis
        }.replaceWithVoid()
    }
}