package com.andredonadon.application.resource

import com.andredonadon.application.dto.ReviewDTO
import com.andredonadon.application.usecase.SaveReviewUseCase
import com.andredonadon.domain.model.Review
import com.andredonadon.domain.repository.ReviewRatingRepository
import com.andredonadon.domain.service.ReviewCommentService
import io.smallrye.mutiny.Uni
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.util.UUID

@Path("api/v1/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class ReviewResource(
    private val reviewRatingRepository: ReviewRatingRepository,
    private val saveReviewUseCase: SaveReviewUseCase,
    private val reviewCommentService: ReviewCommentService
) {

    @POST
    @Path("/{restaurantId}")
    fun saveRestaurantReview(
        @PathParam("restaurantId") restaurantId: UUID,
        reviewDTO: ReviewDTO
    ): Uni<Response> {
        return saveReviewUseCase.execute(reviewDTO.copy(restaurantId = restaurantId))
            .map { Response.status(Response.Status.ACCEPTED).build() }
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

    @GET
    @Path("/{restaurantId}/list")
    fun getReviews(
        @PathParam("restaurantId") restaurantId: UUID,
        @QueryParam("page") page: Int?,
        @QueryParam("quantity") quantity: Int?
    ): Uni<List<ReviewResponse>> {
        val safePage = page ?: 1
        val safeQuantity = quantity ?: 10

        return reviewCommentService.listReviews(restaurantId, safePage, safeQuantity)
            .map { reviews -> reviews.map { ReviewResponse(it) } }
    }
}

data class ReviewRatingResponse(
    val totalReviews: Int,
    val averageRating: Double
)

data class ReviewResponse(
    val customerName: String,
    val rating: Int,
    val comment: String?,
    val createdAt: String
) {
    constructor(review: Review) : this(
        customerName = review.customerName,
        rating = review.rating,
        comment = review.comment,
        createdAt = review.createdAt.toString()
    )
}