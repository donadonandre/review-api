package com.andredonadon.infrastructure.client

import com.andredonadon.domain.model.Restaurant
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@Path("/restaurants")
@RegisterRestClient
interface RestaurantServiceClient {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getRestaurant(@PathParam("id") restaurantId: String): Restaurant
}
