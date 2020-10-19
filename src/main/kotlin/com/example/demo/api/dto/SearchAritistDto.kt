package com.example.demo.api.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Artist(

    @JsonProperty("id")
    val id: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("uri")
    val uri: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Artists(

    @JsonProperty("items")
    val artists: List<Artist>

)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SearchArtistResponse(

    @JsonProperty("artists")
    val artists: Artists
)
