package com.example.demo.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GetRelatedArtistsResponse(

    @JsonProperty("artists")
    val artists: List<Artist>
)
