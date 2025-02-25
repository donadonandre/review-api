package com.andredonadon.application.resource

import com.andredonadon.application.dto.ReviewDTO
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.MediaType

@Path("api/v1/review")
class ReviewResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    fun saveReview(reviewDTO: ReviewDTO) {
    }

}