package com.andredonadon.application.resource

import com.andredonadon.application.dto.ReviewDTO
import com.andredonadon.application.usecase.SaveReviewUseCase
import com.andredonadon.domain.repository.ReviewRatingRepository
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.util.UUID

@Path("api/v1/review")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ReviewResource(
    private val reviewRatingRepository: ReviewRatingRepository,
    private val saveReviewUseCase: SaveReviewUseCase
) {

    @POST
    @Path("/{restaurantId}")
    fun saveRestaurantReview(
        @PathParam("restaurantId") restaurantId: UUID,
        reviewDTO: ReviewDTO
    ): Uni<Response> {
        return saveReviewUseCase.execute(reviewDTO.copy(restaurantId = restaurantId))
            .map { Response.status(Response.Status.CREATED).build() }
    }

    @GET
    @Path("/{restaurantId}")
    fun getRestaurantRating(@PathParam("restaurantId") restaurantId: UUID): Uni<ReviewRatingResponse> {
        return reviewRatingRepository.findByRestaurantId(restaurantId)
            .map { reviewRating ->
                reviewRating?.let { ReviewRatingResponse(it.totalReviews, it.averageRating) }
                    ?: ReviewRatingResponse(0, 0.0)
            }
    }
}

data class ReviewRatingResponse(
    val totalReviews: Int,
    val averageRating: Double
)